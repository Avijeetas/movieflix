package com.movieflix.dto;

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

    @NotBlank(message = "Title should not be null or blank")
    private String title;


    @NotEmpty(message = "Name should not be null or blank")
    private Set<String> directors;
    @NotEmpty(message = "countries should not be null or blank")
    private Set<String> countries;
    @NotEmpty(message = "actors should not be null or blank")
    private Set<String> movieCast;


    @NotEmpty(message = "genre should not be null or blank")
    private Set<String> genre;


    @NotBlank(message = "Description should not be null or blank")
    private String description;

    private Integer releaseYear;
    private Integer duration;
    @NotBlank(message = "Poster should not be null or blank")
    private String poster;

    @NotBlank(message = "Poster should not be null or blank")
    private String posterUrl;

    @NotNull
    private Boolean isDeleted;


}
