package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.entities.Movie;
import com.movieflix.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;


@Service
public class MovieServiceImpl implements MovieService{
    private final FileService fileService;
    private final MovieRepository movieRepository;
    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(FileService fileService, MovieRepository movieRepository) {
        this.fileService = fileService;
        this.movieRepository = movieRepository;
    }

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        // upload the file
        String uploadedFileName = fileService.uploadFile(path, file );
        // set the value of field 'poster' as filename,
        movieDto.setPoster(uploadedFileName);
        // map dto to entity
        Movie movie = new Movie(movieDto.getId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster(),
                0);
        // save the movie object and return movie object
        Movie savedMovie = movieRepository.save(movie);
        // generate the poster url


        return convertToDto(savedMovie);
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        // check the data in db and if exists fetch the data
        Movie movie = movieRepository.findById(movieId).orElseThrow(()-> new RuntimeException("Movie not found"));

        return convertToDto(movie);
    }

    private MovieDto convertToDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                baseUrl + "/file/" + movie.getPoster(),
                movie.getIsDeleted()
        );
    }

    @Override
    public List<MovieDto> getMovies() {
        List<Movie> movies = movieRepository.findAll();

        return movies
                .stream().map(this::convertToDto).toList();
    }
}
