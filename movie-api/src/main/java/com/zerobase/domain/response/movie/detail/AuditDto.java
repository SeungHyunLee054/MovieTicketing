package com.zerobase.domain.response.movie.detail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AuditDto {
    private String auditNo;
    private String watchGradeNm;
}
