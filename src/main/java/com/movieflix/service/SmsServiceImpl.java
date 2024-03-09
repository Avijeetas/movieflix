package com.movieflix.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 03-09-2024
 **/
@Service
@Slf4j
public class SmsServiceImpl implements SmsService{
    @Value("${twilio.accountSid}")
    private String accountSid;
    @Value("${twilio.authToken}")
    private String authToken;
    @Value("${twilio.fromNumber}")
    private String fromNumber;
    @Value("${twilio.active}")
    private Boolean isActive;
    @Override
    public void sendSms(String toNumber, String messageBody) {
        if(!isActive){
            log.info("Twilio service is not active, unable to send messages");
            return;
        }
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(fromNumber),
                        messageBody)
                .create();
        log.info("SMS sent with SID {}", message.getSid());
    }
}
