package com.movieflix.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MovieDto;
import com.movieflix.dto.MoviePageResponse;
import com.movieflix.repositories.MovieRepository;
import com.movieflix.service.MovieService;
import com.movieflix.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/movies/")

public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("add")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws IOException {
        MovieDto dto = convertToMovieDto(movieDto);

        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("{movieId}")
    public ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){

        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.FOUND);
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("update")
    public ResponseEntity<MovieDto> updateMovieHandler(@RequestPart MultipartFile file,
                                                       @RequestPart String movieDto) throws IOException {
        MovieDto dto = convertToMovieDto(movieDto);

        return ResponseEntity.ok(movieService.update(dto, file));
    }

    @DeleteMapping("{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId) throws IOException {
        movieService.deleteById(movieId);
        return ResponseEntity.noContent().build();
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/populate")
    public ResponseEntity<MovieDto> postingData(@RequestBody String movieDto) throws JsonProcessingException {
        MovieDto dto = convertToMovieDto(movieDto);
        return ResponseEntity.ok(movieService.populateMovies(dto));
    }

    @GetMapping()
    public ResponseEntity<MoviePageResponse> getMoviesPaged(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesPaged(pageNumber, pageSize));
    }

    @GetMapping("sorted")
    public ResponseEntity<MoviePageResponse> getMoviesSorted(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
    ) {
        return ResponseEntity.ok(movieService.getAllMoviesSorted(pageNumber, pageSize, sortBy, sortDir));
    }


}
