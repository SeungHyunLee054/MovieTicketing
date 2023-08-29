package com.zerobase.domain.repository;

import com.zerobase.domain.model.OpenMovie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OpenMovieRepository extends JpaRepository<OpenMovie, Long> {
    Optional<OpenMovie> findByMovieCd(String movieCd);
}
