package com.movieflix.auth.services;

import com.movieflix.auth.entities.User;
import com.movieflix.auth.entities.UserRole;
import com.movieflix.auth.repositories.UserRepository;
import com.movieflix.utils.AppConstants;
import com.movieflix.utils.AuthResponse;
import com.movieflix.utils.LoginRequest;
import com.movieflix.utils.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-17-2024
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
   private final PasswordEncoder passwordEncoder;
   private final UserRepository userRepository;
   private final RefreshTokenService refreshTokenService;
   private final JwtService jwtService;
   private final AuthenticationManager authenticationManager;
   public AuthResponse register(RegisterRequest registerRequest) {
      var user = User
              .builder()
              .name(registerRequest.getName())
              .email(registerRequest.getEmail())
              .password(passwordEncoder.encode(registerRequest.getPassword()))
              .username(registerRequest.getUsername())
              .role(UserRole.USER)
              .build();

      User saveUser = userRepository.save(user);

      log.info("User has been created with user email {}",user.getEmail());
      return generateAuthResponse(saveUser);
   }

   public AuthResponse generateAuthResponse(User saveUser) {
      var accessToken = jwtService.generateToken(saveUser);
      var refreshToken = refreshTokenService.createRefreshToken(saveUser.getEmail());
      return AuthResponse
              .builder()
              .accessToken(accessToken)
              .refreshToken(refreshToken.getRefreshToken())
              .build();
   }

   public AuthResponse login(LoginRequest loginRequest){
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      loginRequest.getEmail(),
                      loginRequest.getPassword()));

      var user = userRepository.findByUsername(loginRequest.getEmail()).orElseThrow(()->
              new UsernameNotFoundException(AppConstants.USER_NOT_FOUND_WITH_EMAIL));
      log.info("User has been logged in with user email {}",user.getEmail());
      return generateAuthResponse(user);
   }
}
