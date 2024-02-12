package com.movieflix.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-11-2024
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
   private String accessToken;
   private String refreshToken;
}