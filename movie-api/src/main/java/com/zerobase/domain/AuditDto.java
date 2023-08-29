package com.zerobase.domain;

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
