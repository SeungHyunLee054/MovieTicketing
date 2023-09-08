package com.zerobase.domain.repository;

import com.zerobase.domain.model.OpenMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OpenMovieRepository extends JpaRepository<OpenMovie, Long> {
    Optional<OpenMovie> findByMovieCd(String movieCd);

    @Transactional
    void deleteByMovieCd(String movieCd);
}
