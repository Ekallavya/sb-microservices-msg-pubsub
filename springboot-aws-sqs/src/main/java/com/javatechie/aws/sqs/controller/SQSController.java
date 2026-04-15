package com.javatechie.aws.sqs.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SQSController {
    Logger logger= LoggerFactory.getLogger(SQSController.class);
    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    @GetMapping("/sendmsg/{msg}")
    public String sendMessageToQueue(@PathVariable String msg) {
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(msg).build());
        logger.info("Message sent to queue: " + msg);
        return "message sent to queue: " + msg;
    }

    @SqsListener("aws-demo-queue")
    public void loadMessageFromSQS(String message)  {
        logger.info("message from SQS Queue {}",message);
    }
}
