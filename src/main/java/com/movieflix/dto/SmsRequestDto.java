package com.movieflix.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 03-09-2024
 **/
@Getter
public class SmsRequestDto {
    private String toNumber;
    private String messageBody;
}
