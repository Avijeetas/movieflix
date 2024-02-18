package com.movieflix.auth.services;

import com.movieflix.auth.dto.UserDto;
import com.movieflix.auth.entities.User;
import com.movieflix.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-17-2024
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
   private final UserRepository userRepository;

   public List<UserDto> getAllEnabledUsers(){
      return userRepository.findAllByIsEnabled(Boolean.TRUE)
              .stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
   }
   public List<UserDto> getAllDisabledUsers(){
      return userRepository.findAllByIsEnabled(Boolean.FALSE)
              .stream()
              .map(this::convertToDto)
              .collect(Collectors.toList());
   }
   private UserDto convertToDto(User user){
      return UserDto.builder()
              .email(user.getEmail())
              .name(user.getName())
              .role(user.getRole())
              .username(user.getUsername())
              .isEnabled(user.isEnabled())
              .isCredentialsNonExpired(user.isCredentialsNonExpired())
              .isAccountNonExpired(user.isAccountNonExpired())
              .isAccountNonLocked(user.isAccountNonLocked())
              .build();
   }
}
