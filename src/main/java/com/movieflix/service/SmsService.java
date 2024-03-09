package com.movieflix.service;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 03-09-2024
 **/
public interface SmsService {
    public void sendSms(String toNumber, String messageBody);
}
