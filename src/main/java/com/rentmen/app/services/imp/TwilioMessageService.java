package com.rentmen.app.services.imp;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioMessageService {

    @Value("${twilio.account-sid}")
    private String accountSid;

    @Value("${twilio.auth-token}")
    private String authToken;

    @Value("${twilio.phone-number}")
    private String fromPhoneNumber;

    public String sendSMS(String To, String message) {

        sendingSms(To, message);
        sendingSms("+31648908818",message);

        return "Message sent successfully";
    }

    public void sendProjectInfoSMS(Long projectId, String name) {
        sendingSms("+31648908818", "ProjectID For " + name + " - " + "TimeClock Config: " + projectId);
        System.out.println("JOB ID SENT");
    }

    private void sendingSms(String to, String message) {
        Twilio.init(accountSid, authToken);
//        Message.creator(new PhoneNumber(to),
//                new PhoneNumber(fromPhoneNumber), message).create();
    }
}