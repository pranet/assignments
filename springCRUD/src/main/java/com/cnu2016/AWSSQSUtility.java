package com.cnu2016;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.GetQueueUrlRequest;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

/**
 * Created by pranet on 11/07/16.
 */
public class AWSSQSUtility {
    private String queueName;
    private String  queueURL;
    private AmazonSQS sqs;
    public static volatile AWSSQSUtility awssqsUtility = new AWSSQSUtility();

    private AWSSQSUtility() {
        queueName = "cnu2016_pverma_assignment05";
        sqs = new AmazonSQSClient();
        sqs.setEndpoint("https://sqs.us-east-1.amazonaws.com");
        queueURL = getQueueUrl(queueName);
    }

    public String getQueueUrl(String queueName) {
        GetQueueUrlRequest getQueueUrlRequest = new GetQueueUrlRequest(queueName);
        return this.sqs.getQueueUrl(getQueueUrlRequest).getQueueUrl();
    }

    public static AWSSQSUtility getInstance() {
        return awssqsUtility;
    }

    public AmazonSQS getAWSSQSClient() {
        return awssqsUtility.sqs;
    }

    public void sendMessageToQueue(String message) {
        sqs.sendMessage(new SendMessageRequest(queueURL, message));
    }
}
