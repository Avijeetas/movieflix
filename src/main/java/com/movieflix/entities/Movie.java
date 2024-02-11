package com.movieflix.entities;

import com.movieflix.utils.AppConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    @NotBlank(message = AppConstants.TITLE_SHOULD_NOT_BE_NULL_OR_BLANK)
    private String title;


    @ElementCollection
    @CollectionTable(name="movie_directors")
    private Set<String> directors;

    @ElementCollection
    @CollectionTable(name="countries")
    private Set<String> countries;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;

    @ElementCollection
    @CollectionTable(name="genre")
    private Set<String> genre;

    private Integer releaseYear;
    private Integer duration;
    @Column(nullable=false)
    @NotBlank(message = AppConstants.POSTER_SHOULD_NOT_BE_NULL_OR_BLANK)

    private String poster;
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private Boolean isDeleted;
}
