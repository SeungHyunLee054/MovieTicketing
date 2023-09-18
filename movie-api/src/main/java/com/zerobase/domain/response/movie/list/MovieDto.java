package com.zerobase.domain.response.movie.list;

import com.zerobase.domain.model.Movie;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieDto {
    private String movieCd;
    private String movieNm;
    private String movieNmEn;
    private String prdtYear;
    private String openDt;
    private String typeNm;
    private String prdtStatNm;
    private String nationAlt;
    private String genreAlt;
    private String repNationNm;
    private String repGenreNm;
    private List<DirectorDto> directors;
    private List<CompanyDto> companys;

    public static Movie from(MovieDto movieDto) {
        StringBuilder directorSb = new StringBuilder();
        for (int i = 0; i < movieDto.getDirectors().size(); i++) {
            if (i == movieDto.getDirectors().size() - 1) {
                directorSb.append(movieDto.getDirectors().get(i).getPeopleNm());
            } else if (i == 2) {
                directorSb.append(movieDto.getDirectors().get(i).getPeopleNm()).append(", 이하 생략");
                break;
            } else {
                directorSb.append(movieDto.getDirectors().get(i).getPeopleNm()).append(", ");
            }
        }

        StringBuilder companySb = new StringBuilder();
        for (int i = 0; i < movieDto.getCompanys().size(); i++) {
            if (i >= 3) {
                break;
            }
            if (i == movieDto.getCompanys().size() - 1) {
                companySb.append(movieDto.getCompanys().get(i).getCompanyNm());
            } else if (i == 2) {
                companySb.append(movieDto.getCompanys().get(i).getCompanyNm()).append(", 이하 생략");
            } else {
                companySb.append(movieDto.getCompanys().get(i).getCompanyNm()).append(", ");
            }
        }


        return Movie.builder()
                .movieCd(movieDto.getMovieCd())
                .movieName(movieDto.getMovieNm())
                .movieNameEn(movieDto.getMovieNmEn())
                .prdtYear(movieDto.getPrdtYear())
                .openDt(movieDto.getOpenDt().isEmpty() ? null : LocalDate.parse(movieDto.getOpenDt(), DateTimeFormatter.ofPattern("yyyyMMdd")))
                .typeName(movieDto.getTypeNm())
                .prdtStatName(movieDto.getPrdtStatNm())
                .nationAlt(movieDto.getNationAlt())
                .genreAlt(movieDto.getGenreAlt())
                .directorName(directorSb.toString())
                .companyName(companySb.toString())
                .build();
    }
}
