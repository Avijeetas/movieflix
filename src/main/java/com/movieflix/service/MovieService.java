package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.exceptions.FileExistsException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException, FileExistsException;

    MovieDto getMovie(Integer movieId);

    List<MovieDto> getMovies();

    MovieDto update(MovieDto movieDto, MultipartFile file) throws IOException, FileExistsException;

    void deleteById(Integer movieId) throws IOException;

    MovieDto populateMovies(MovieDto movieDto);
}
