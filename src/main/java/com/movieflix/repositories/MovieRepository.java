package com.movieflix.repositories;

import com.movieflix.entities.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Optional<Movie> findMovieByIdAndIsDeleted(Integer movieId, Boolean isDeleted);

   List<Movie> findAllByIsDeleted(Boolean isDeleted);
    Page<Movie> findAllByIsDeleted(Boolean isDeleted, Pageable pageable);

}
