package com.movieflix.controllers;

import com.movieflix.dto.SmsRequestDto;
import com.movieflix.service.SmsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 03-09-2024
 **/
@RestController
@RequestMapping("api/v1/sms")
public class SmsController {


    public final SmsService smsService;

    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }


    @PostMapping
    public ResponseEntity<Void> sendSms(@RequestBody SmsRequestDto smsRequestdto) {
        smsService.sendSms(smsRequestdto.getToNumber(), smsRequestdto.getMessageBody());
        return ResponseEntity.ok().build();
    }

}