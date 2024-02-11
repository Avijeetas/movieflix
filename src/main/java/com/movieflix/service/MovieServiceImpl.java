package com.movieflix.service;

import com.movieflix.dto.MovieDto;
import com.movieflix.entities.Movie;
import com.movieflix.exceptions.EmptyFileException;
import com.movieflix.exceptions.FileExistsException;
import com.movieflix.exceptions.MovieNotFoundException;
import com.movieflix.repositories.MovieRepository;

import com.movieflix.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
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
            throw new EmptyFileException(AppConstants.FILE_NOT_UPLOADED_MSG);
        }

        if(Files.exists(Paths.get(path+ File.separator + file.getOriginalFilename()))){
            throw new FileExistsException(
                    String.format(AppConstants.FILE_ALREADY_EXISTS_WITH_FILE_NAME, file.getOriginalFilename()));
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

    private Movie convertToEntity(MovieDto movieDto) {
        return new Movie(movieDto.getId(),
                movieDto.getTitle(),
                movieDto.getDirectors(),
                movieDto.getCountries(),
                movieDto.getMovieCast(),
                movieDto.getGenre(),
                movieDto.getReleaseYear(),
                movieDto.getDuration(),
                movieDto.getPoster(),
                movieDto.getDescription(),
                movieDto.getIsDeleted());
    }

    @Override
    public MovieDto getMovie(Integer movieId) {
        // check the data in db and if exists fetch the data
        Movie movie = getMovieById(movieId);

        return convertToDto(movie);
    }

    private Movie getMovieById(Integer movieId) {
        return movieRepository.findMovieByIdAndIsDeleted(movieId, Boolean.FALSE)
                .orElseThrow(()-> new MovieNotFoundException(
                        String.format(AppConstants.MOVIE_NOT_FOUND_WITH_ID_MSG, movieId)));
    }

    private MovieDto convertToDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getDirectors(),
                movie.getCountries(),
                movie.getMovieCast(),
                movie.getGenre(),
                movie.getDescription(),
                movie.getReleaseYear(),
                movie.getDuration(),
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
        Movie movie = getMovieById(movieDto.getId());
        MovieDto dto = convertToDto(movie);

        return addMovie(dto, file);
    }

    @Override
    public void deleteById(Integer movieId) throws IOException {
        Movie movie = getMovieById(movieId);

        MovieDto movieDto = convertToDto(movie);
        movieDto.setIsDeleted(true);
        movie = convertToEntity(movieDto);
        movieRepository.save(movie);
        Files.deleteIfExists(Paths.get(path+ File.separator+movie.getPoster()));
    }

    @Override
    public MovieDto populateMovies(MovieDto movieDto) {
        List<Movie> savedMovies = Stream.of(movieDto)
                .map(this::convertToEntity)
                .map(movieRepository::save)  // Save each movie to the repository
                .toList();  // Collect saved movies into a list

        savedMovies.forEach(movie -> log.info("Movie saved with id {}", movie.getId()));

        return convertToDto(savedMovies.getFirst());
    }
}
