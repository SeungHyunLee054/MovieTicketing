package com.zerobase.domain.response.movie.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailDto {
    private String companyCd;
    private String companyNm;
    private String companyNmEn;
    private String companyPartNm;
}
