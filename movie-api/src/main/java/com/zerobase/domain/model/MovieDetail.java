package com.zerobase.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MovieDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private Long id;
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String movieNmOg;
    private String prdtYear;
    private String showTm;
    private String openDt;
    private String typeNm;
    private String nations;
    private String genreNm;
    private String directors;
    private String actors;
    private String audits;
    private String companys;
}
