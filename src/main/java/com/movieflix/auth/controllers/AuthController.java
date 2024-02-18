package com.movieflix.auth.controllers;

import com.movieflix.auth.dto.UserDto;
import com.movieflix.auth.entities.RefreshToken;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.repositories.UserRepository;
import com.movieflix.auth.services.AuthService;
import com.movieflix.auth.services.JwtService;
import com.movieflix.auth.services.RefreshTokenService;
import com.movieflix.auth.services.UserService;
import com.movieflix.utils.AuthResponse;
import com.movieflix.utils.LoginRequest;
import com.movieflix.utils.RefreshTokenRequest;
import com.movieflix.utils.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-11-2024
 **/
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          RefreshTokenService refreshTokenService
            ,UserService userService,
                          UserRepository userRepository) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("refresh")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();

        return ResponseEntity
                .ok(authService.generateAuthResponse(user));

    }

    @PutMapping("all")
    public ResponseEntity<List<UserDto>> updateUserInfos(){
        return ResponseEntity
                .ok(userService.getAllEnabledUsers());
    }
    @DeleteMapping()
    public ResponseEntity<String> deleteRecords(){
        userRepository.deleteAll();
        return ResponseEntity.ok("Done");

    }
}
