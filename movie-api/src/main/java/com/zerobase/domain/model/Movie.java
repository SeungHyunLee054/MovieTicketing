package com.zerobase.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie extends BaseEntity {
    @Column(unique = true)
    private String movieCd;
    private String movieName;
    private String movieNameEn;
    private String prdtYear;
    private String openDt;
    private String typeName;
    private String prdtStatNm;
    private String nationAlt;
    private String genreAlt;
    private String directorName;
    private String companyName;
}
