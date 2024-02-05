package com.movieflix.entities;

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
    @NotBlank(message = "Title should not be null or blank")
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
    @NotBlank(message = "Poster should not be null or blank")

    private String poster;
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    private Boolean isDeleted;
}
