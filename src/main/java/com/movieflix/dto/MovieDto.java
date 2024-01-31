package com.movieflix.dto;

import jakarta.validation.constraints.NotBlank;
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


    @NotBlank(message = "Name should not be null or blank")

    private String director;
    @NotBlank(message = "Studio should not be null or blank")

    private String studio;

    private Set<String> movieCast;



    private Integer releaseYear;

    @NotBlank(message = "Poster should not be null or blank")
    private String poster;

    @NotBlank(message = "Poster should not be null or blank")
    private String posterUrl;

}
