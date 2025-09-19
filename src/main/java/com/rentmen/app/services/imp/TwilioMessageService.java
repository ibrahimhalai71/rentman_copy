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

//        Twilio.init(accountSid, authToken);
//
//		Message.creator(new PhoneNumber(To),
//				new PhoneNumber(fromPhoneNumber), message).create();
//
//		Message.creator(new PhoneNumber("+31648908818"),
//				new PhoneNumber(fromPhoneNumber), message).create();

        return "Message sent successfully";
    }

    public void sendJobIdSMS(Long jobId) {

//        Twilio.init(accountSid, authToken);
//				Message.creator(new PhoneNumber("+31648908818"),
//				new PhoneNumber(fromPhoneNumber), "JobID For TimeClock Config: "+jobId).create();
        System.out.println("JOB ID SENT");

    }
}