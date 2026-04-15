package com.javatechie.aws.sns.controller;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.SubscribeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sns")
public class SnsController {
    @Autowired
    private AmazonSNSClient snsClient;
    @Value("${cloud.aws.end-point.uri}")
    String TOPIC_ARN;

    @GetMapping("/addSubscriptionEmail/{email}")
    public String addSubscriptionEmail(@PathVariable String email) {
        SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "email", email);
        snsClient.subscribe(request);
        return "Subscription request is pending. To confirm the subscription, check your email : " + email;
    }
    @GetMapping("/addSubscriptionPhone/{phone}")
    public String addSubscriptionPhone(@PathVariable String phone) {
        SubscribeRequest request = new SubscribeRequest(TOPIC_ARN, "sms", phone);
        snsClient.subscribe(request);
        return "Subscription request is pending. To confirm the subscription, check your phone : " + phone;
    }

    @GetMapping("/sendNotification")
    public String publishMessageToTopic(){
        PublishRequest publishRequest=new PublishRequest(TOPIC_ARN,buildEmailBody(),"Notification: Network connectivity issue");
        snsClient.publish(publishRequest);
        return "Notification send successfully !!";
    }



    private String buildEmailBody(){
        return "Dear Employee ,\n" +
                "\n" +
                "\n" +
                "Connection down Bangalore."+"\n"+
                "All the servers in Bangalore Data center are not accessible. We are working on it ! \n" +
                "Notification will be sent out as soon as the issue is resolved. For any questions regarding this message please feel free to contact IT Service Support team";
    }
}
