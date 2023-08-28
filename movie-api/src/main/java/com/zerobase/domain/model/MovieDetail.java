package com.zerobase.domain.model;

import com.zerobase.domain.MovieDetailDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MovieDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(unique = true)
    private String movieCd;
    private String movieName;
    private String movieNameEn;
    private String movieNameOg;
    private String prdtYear;
    private String showTm;
    private String openDt;
    private String typeName;
    private String nations;
    private String genreName;
    private String directors;
    private String actors;
    private String watchGradeName;
    private String companys;
}
