package com.zerobase.domain.repository;

import com.zerobase.domain.model.Movie;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomMovieSearchRepository {
    List<Movie> searchByMovieName(String name, Pageable pageable);
}
