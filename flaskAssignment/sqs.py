import boto.sqs
import json
import datetime
import time
import config
from sqlalchemy import *


# Connect to database
metadata = MetaData()
db = create_engine("mysql+pymysql://" + config.username + ":" + config.password + "@" + config.host + "/" + config.database);
connection = db.connect()

def populateAuditLog(row):
    timestamp = row['timeStamp']
    timestamp = datetime.datetime(timestamp['weekyear'], timestamp['monthOfYear'], timestamp['dayOfMonth'], timestamp['hourOfDay'],
                      timestamp['minuteOfHour'], timestamp['secondOfMinute']).isoformat()
    table = Table('audit_log', metadata, autoload=True, autoload_with=db)
    assert table != None
    ins = table.insert().values(url=row['url'],
                                parameters=row['parametersString'],
                                responseCode=row['responseCode'],
                                ipAddress=row['ipAddress'],
                                timestamp=timestamp,
                                requestTime=row['requestTime'],
                                requestType=row['requestType'])
    connection.execute(ins)

# Connect to SQS queue
conn = boto.sqs.connect_to_region(config.region)
Q = conn.get_queue(config.queueName)
while True:
    m = Q.read()
    if m == None:
        print 'No data found. Sleeping for 5s'
        time.sleep(5)
    else:
        print 'Inserting' + m.get_body();
        dict = json.loads(m.get_body())
        populateAuditLog(dict)
        m.delete()