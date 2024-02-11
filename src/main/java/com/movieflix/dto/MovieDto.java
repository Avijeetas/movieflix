package com.movieflix.dto;

import com.movieflix.utils.AppConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Integer id;

    @NotBlank(message = AppConstants.TITLE_SHOULD_NOT_BE_NULL_OR_BLANK)
    private String title;


    @NotEmpty(message = AppConstants.NAME_SHOULD_NOT_BE_NULL_OR_BLANK)
    private Set<String> directors;
    @NotEmpty(message = AppConstants.COUNTRIES_SHOULD_NOT_BE_NULL_OR_BLANK)
    private Set<String> countries;
    @NotEmpty(message = AppConstants.ACTORS_SHOULD_NOT_BE_NULL_OR_BLANK)
    private Set<String> movieCast;


    @NotEmpty(message = AppConstants.GENRE_SHOULD_NOT_BE_NULL_OR_BLANK)
    private Set<String> genre;


    @NotBlank(message = AppConstants.DESCRIPTION_SHOULD_NOT_BE_NULL_OR_BLANK)
    private String description;

    private Integer releaseYear;
    private Integer duration;
    @NotBlank(message = AppConstants.POSTER_SHOULD_NOT_BE_NULL_OR_BLANK)
    private String poster;

    @NotBlank(message = AppConstants.POSTER_URL_NOT_BLANK_OR_NULL_MSG)
    private String posterUrl;

    @NotNull
    private Boolean isDeleted;


}
