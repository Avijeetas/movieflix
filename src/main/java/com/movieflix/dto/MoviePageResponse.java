package com.movieflix.dto;

/**
 * @author Avijeet
 * @project movieApi
 * @github avijeetas
 * @date 02-18-2024
 **/


import java.util.List;

public record MoviePageResponse(List<MovieDto> movieDtos,
                                Integer pageNumber,
                                Integer pageSize,
                                long totalElements,
                                int totalPages,
                                boolean isLast) {
}
