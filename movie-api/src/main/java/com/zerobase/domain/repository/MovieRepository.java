package com.zerobase.domain.repository;

import com.zerobase.domain.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    Optional<Movie> findByMovieCd(String movieCd);

    List<Movie> findAllByOpenDtBetween(LocalDate nowMinusMonth, LocalDate now);
}
