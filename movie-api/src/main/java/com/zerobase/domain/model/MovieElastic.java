package com.zerobase.domain.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "movies")
@Mapping(mappingPath = "/elasticsearch/movie-mapping.json")
@Setting(settingPath = "elasticsearch/movie-setting.json")
public class MovieElastic {
    @Id
    private Long id;
    private String movieCd;
    private String movieName;
    private String movieNameEn;
}
