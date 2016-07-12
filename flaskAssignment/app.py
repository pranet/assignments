from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
import config
import dateutil.parser
import datetime
# Connect to database
app = Flask(__name__)
app.config['SQLALCHEMY_DATABASE_URI'] = "mysql+pymysql://" + config.username + ":" + config.password + "@" + config.host + "/" + config.database
db = SQLAlchemy(app)


def date_to_iso(date):
    date = dateutil.parser.parse(str(date)).isoformat()
    return datetime.datetime.strptime(str(date), '%Y-%m-%dT%H:%M:%S').strftime('%m/%d/%YT%H:%M:%S')
class audit_log(db.Model):
    id = db.Column(db.INTEGER, primary_key=True)
    timestamp = db.Column(db.DATETIME)
    url = db.Column(db.VARCHAR)
    parameters = db.Column(db.VARCHAR)
    responseCode = db.Column(db.INTEGER)
    ipAddress = db.Column(db.VARCHAR)
    requestTime = db.Column(db.INTEGER)
    requestType = db.Column(db.INTEGER)

    def __init__(self, id, timestamp, url, parameters, responseCode, ipAddress, requestTime, requestType):
        self.id = id
        self.timestamp = timestamp
        self.url = url
        self.parameters = parameters
        self.responseCode = responseCode
        self.ipAddress = ipAddress
        self.requestTime = requestTime
        self.requestType = requestType

    def to_json(self):
        return {
            "id" : self.id,
            "timestamp" : date_to_iso(self.timestamp),
            "url" : self.url,
            "request_type" : self.requestType,
            "parameters" : self.parameters,
            "request_duration_ms" : self.requestTime,
            "response_code" : self.responseCode,
            "request_ip_address" : self.ipAddress
        }

@app.route("/api/auditLogs/", methods =['GET'])
def get_audit_log():
    limit = max(0,min(int(request.args.get('limit', 10)), 10))
    offset = max(request.args.get('offset', 0), 0)
    startTime = dateutil.parser.parse(request.args.get('startTime', '1900-01-01 00:00:00'))
    endTime = dateutil.parser.parse(request.args.get('endTime', '3100-01-01 00:00:00'))
    data = db.session.query(audit_log)
    data = data.filter(audit_log.timestamp.between(startTime, endTime))
    data = data.order_by(audit_log.timestamp.desc())
    data = data.limit(limit)
    data = data.offset(offset)
    return jsonify(data=[logs.to_json() for logs in data])

if __name__ == '__main__':
    print 'k'
    app.run(debug=True)