import boto.sqs
import json
import datetime
import time
import config
import logging
from sqlalchemy import *

def populate_audit_log(row):
    timestamp = row['timeStamp']
    timestamp = datetime.datetime(timestamp['weekyear'], timestamp['monthOfYear'], timestamp['dayOfMonth'], timestamp['hourOfDay'],
                      timestamp['minuteOfHour'], timestamp['secondOfMinute']).isoformat()
    table = Table('audit_log', metadata, autoload=True, autoload_with=db)
    ins = table.insert().values(url=row['url'],
                              parameters=row['parametersString'],
                              responseCode=row['id'],
                              ipAddress=row['ipAddress'],
                              timestamp=timestamp)
    connection.execute(ins)

def run():
    while True:
        m = Q.read()
        if m == None:
            log.warn('No data found. Sleeping for 5s')
            time.sleep(5)
        else:
            log.warn('Inserting' + m.get_body())
            dict = json.loads(m.get_body())
            populate_audit_log(dict)
            m.delete()

if __name__ == '__main__':
    # Setup logger
    log = logging.getLogger()
    log.addHandler(logging.StreamHandler())
    # Connect to database
    metadata = MetaData()
    db = create_engine(
        "mysql+pymysql://" + config.username + ":" + config.password + "@" + config.host + "/" + config.database);
    connection = db.connect()

    # Connect to SQS queue
    conn = boto.sqs.connect_to_region(config.region)
    Q = conn.get_queue(config.queueName)

    run()