package com.movieflix.auth.services;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UriTemplate;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-11-2024
 **/

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthFilterService extends OncePerRequestFilter {

    private final RestTemplate restTemplate;
    @Value("${base.auth_url}")
    private String AUTH_URL;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String authHeader=  request.getHeader("Authorization");
        String apiName = null;
        apiName = (request.getRequestURI());
        log.info("apiname : {}", apiName);
        //testing
        if(apiName.equals("/api/v1/sms")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (apiName !=null && (authHeader == null || !authHeader.startsWith("Bearer "))){

            log.info("You need to login/register");
            return;
        }
        // extract jwt
        assert authHeader!=null;
        String jwt = authHeader.substring(7);
        ResponseEntity<Boolean> responseFromSSO;
        try {
            responseFromSSO = restTemplate.getForEntity(AUTH_URL + "/validate?token=" + jwt, Boolean.class);
        } catch (RestClientException ex) {
            log.error("Error while calling authentication service", ex);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        // Check if authentication was successful
        if (responseFromSSO.getStatusCode() == HttpStatus.OK && Objects.equals(responseFromSSO.getBody(), Boolean.TRUE)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }


        if(Objects.equals(responseFromSSO.getBody(), Boolean.TRUE)){
                filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
