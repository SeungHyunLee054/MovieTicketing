package com.zerobase.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity()
public class OpenMovie extends BaseEntity {
    @Column(unique = true)
    private String movieCd;
    private String movieName;
    private String movieNameEn;
    private String prdtYear;
    private LocalDate openDt;
    private String typeName;
    private String prdtStatName;
    private String nationAlt;
    private String genreAlt;
    private String directorName;
    private String companyName;
}
