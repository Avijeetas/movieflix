package com.movieflix.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
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

    @Column(nullable=false)
    @NotBlank(message = "Name should not be null or blank")
    private String director;

    @Column(nullable=false)
    @NotBlank(message = "Studio should not be null or blank")
    private String studio;

    @ElementCollection
    @CollectionTable(name="movie_cast")
    private Set<String> movieCast;

   private Integer releaseYear;

    @Column(nullable=false)
    @NotBlank(message = "Poster should not be null or blank")
    private String poster;
}
