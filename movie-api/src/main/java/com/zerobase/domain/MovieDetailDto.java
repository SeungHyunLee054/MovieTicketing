package com.zerobase.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDetailDto {
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String movieNmOg;
    private String showTm;
    private String prdtYear;
    private String openDt;
    private String prdtStatNm;
    private String typeNm;
    private List<NationDto> nations;
    private List<GenreDto> genres;
    private List<DirectorDetailDto> directors;
    private List<ActorDto> actors;
    private List<ShowTypeDto> showTypes;
    private List<CompanyDetailDto> companys;
    private List<AuditDto> audits;
    private List<StaffDto> staffs;
}
