package com.zerobase.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
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
