package com.movieflix.auth.dto;

import com.movieflix.auth.entities.RefreshToken;
import com.movieflix.auth.entities.UserRole;
import com.movieflix.utils.AppConstants;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-17-2024
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String name;
    private String username;

    private String email;

    private RefreshToken refreshToken;

    private UserRole role;

    private boolean isEnabled;

    private boolean isAccountNonExpired;

    private boolean isAccountNonLocked ;


    private boolean isCredentialsNonExpired;
}
