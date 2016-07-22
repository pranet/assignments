import boto.sqs
from boto.sqs.message import RawMessage
import time
import uuid
import json

# Pushes a message onto the queue
def addMessageToQueue(message):
    global Q
    data = {
        'submitdate': time.strftime("%Y-%m-%dT%H:%M:%S", time.gmtime()),
        'key': str(uuid.uuid1()),
        'message': str(message)
    }
    # Put the message in the queue
    m = RawMessage()
    m.set_body(json.dumps(data))
    Q.write(m)

# Connect to SQS queue
def init():
    global Q
    conn = boto.sqs.connect_to_region('us-east-1')
    Q = conn.get_queue('cnu2016_pverma_assignment05')
    
    addMessageToQueue("Q created");
