package com.movieflix.exceptions;

import com.movieflix.dto.MovieDto;

public class EmptyFileException extends RuntimeException {
    public EmptyFileException(String message) {
        super(message);
    }
}
