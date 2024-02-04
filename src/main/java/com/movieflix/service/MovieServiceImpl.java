package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.entities.Movie;
import com.movieflix.exceptions.EmptyFileException;
import com.movieflix.exceptions.FileExistsException;
import com.movieflix.exceptions.MovieNotFoundException;
import com.movieflix.repositories.MovieRepository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        if(file.isEmpty()){
            throw new EmptyFileException("File not uploaded");
        }

        if(Files.exists(Paths.get(path+ File.separator + file.getOriginalFilename()))){
            throw new FileExistsException("File already exists with id = " + movieDto.getId());
        }
        // upload the file
        String uploadedFileName = fileService.uploadFile(path, file );
        // set the value of field 'poster' as filename,
        movieDto.setPoster(uploadedFileName);
        movieDto.setIsDeleted(false);
        // map dto to entity
        Movie movie = convertToEntity(movieDto);
        // save the movie object and return movie object
        Movie savedMovie = movieRepository.save(movie);
        // generate the poster url


        return convertToDto(savedMovie);
    }

    private static Movie convertToEntity(MovieDto movieDto) {
        return new Movie(movieDto.getId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster(),
                movieDto.getIsDeleted());
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        // check the data in db and if exists fetch the data
        Movie movie = movieRepository.findMovieByIdAndIsDeleted(movieId, Boolean.FALSE)
                .orElseThrow(()-> new MovieNotFoundException("Movie not found with id = " + movieId));

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
        List<Movie> movies = movieRepository.findAllByIsDeleted(false);

        return movies
                .stream()
                .map(this::convertToDto).toList();
    }

    @Override
    public MovieDto update(MovieDto movieDto,  MultipartFile file) throws IOException {
        Movie movie = movieRepository.findMovieByIdAndIsDeleted(movieDto.getId(), Boolean.FALSE)
                .orElseThrow(()-> new MovieNotFoundException("Movie not found with id = " + movieDto.getId()));
        MovieDto dto = convertToDto(movie);

        return addMovie(dto, file);
    }

    @Override
    public void deleteById(Integer movieId) {
        Movie movie = movieRepository.findMovieByIdAndIsDeleted(movieId, Boolean.FALSE)
                .orElseThrow(()-> new MovieNotFoundException("Movie not found with id = " + movieId));

        MovieDto movieDto = convertToDto(movie);
        movieDto.setIsDeleted(true);
        movie = convertToEntity(movieDto);
        movieRepository.save(movie);

        convertToDto(movie);
    }
}
