package com.movieflix.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieflix.dto.MovieDto;
import com.movieflix.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("add")
    public ResponseEntity<MovieDto> addMovieHandler(@RequestPart MultipartFile file,
                                                    @RequestPart String movieDto) throws IOException {
        MovieDto dto = convertToMovieDto(movieDto);

        return new ResponseEntity<>(movieService.addMovie(dto, file), HttpStatus.CREATED);
    }

    @GetMapping("{movieId}")
    private ResponseEntity<MovieDto> getMovieHandler(@PathVariable Integer movieId){
        return ResponseEntity.ok(movieService.getMovie(movieId));
    }
    @GetMapping()
    private ResponseEntity<List<MovieDto>> getMoviesHandler(){
        return ResponseEntity.ok(movieService.getMovies());
    }

    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }

    @PutMapping("{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId,
                                                       @RequestPart MultipartFile file,
                                                       @RequestPart String movieDto) throws IOException {
        if(file.isEmpty()) file = null;

        MovieDto dto = convertToMovieDto(movieDto);
        dto.setId(movieId);

        return ResponseEntity.ok(movieService.update(dto, file));
    }

    @DeleteMapping("{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId) throws IOException {
        movieService.deleteById(movieId);
        return ResponseEntity.noContent().build();
    }

}
