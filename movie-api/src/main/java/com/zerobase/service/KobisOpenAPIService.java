package com.zerobase.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.domain.model.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KobisOpenAPIService {
    private final ObjectMapper mapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();
    private final String API_BASE_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/";
    private final String API_MOVIES_URL = "searchMovieList.json?key=";
    private final String API_MOVIE_DETAIL_URL = "searchMovieInfo.json?key=";
    private final String KEY = System.getProperty("apiKey");

    public JSONObject getMovies(String page) {
        String strUrl = API_BASE_URL
                + API_MOVIES_URL
                + KEY
                + "&curPage=" + page
                + "&itemPerPage=100";

        return restTemplate.getForEntity(strUrl, JSONObject.class).getBody();
    }

    public JSONObject getMovieDetail(String movieCd) {
        String strUrl = API_BASE_URL
                + API_MOVIE_DETAIL_URL
                + KEY
                + "&movieCd=" + movieCd;

        return restTemplate.getForEntity(strUrl, JSONObject.class).getBody();
    }

    public List<Movie> parseMovies(JSONObject jsonObject) {
        List<Movie> movies = new ArrayList<>();
        JSONObject movieListResult = mapper.convertValue(jsonObject.get("movieListResult"), JSONObject.class);
        JSONArray movieList = mapper.convertValue(movieListResult.get("movieList"), JSONArray.class);
        for (Object value : movieList) {
            JSONObject object = mapper.convertValue(value, JSONObject.class);
            JSONArray directors = mapper.convertValue(object.get("directors"), JSONArray.class);
            String director = jsonArrayToStringDirectorsAndActors(new StringBuilder(), directors);

            JSONArray companys = mapper.convertValue(object.get("companys"), JSONArray.class);
            String company = jsonArrayToStringCompanys(new StringBuilder(), companys);

            movies.add(Movie.builder()
                    .movieCd((String) object.get("movieCd"))
                    .movieName((String) object.get("movieNm"))
                    .movieNameEn((String) object.get("movieNmEn"))
                    .prdtYear((String) object.get("prdtYear"))
                    .openDt((String) object.get("openDt"))
                    .typeName((String) object.get("typeNm"))
                    .prdtStatNm((String) object.get("prdtStatNm"))
                    .nationAlt((String) object.get("nationAlt"))
                    .genreAlt((String) object.get("genreAlt"))
                    .directorName(director)
                    .companyName(company)
                    .build());
        }
        return movies;
    }

    public int totalCnt(JSONObject jsonObject) {
        return (int) mapper.convertValue(jsonObject.get("movieListResult"), JSONObject.class).get("totCnt");
    }

    private String jsonArrayToStringDirectorsAndActors(StringBuilder sb, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            if (i >= 3) {
                break;
            }
            JSONObject o = mapper.convertValue(jsonArray.get(i), JSONObject.class);
            if (i == jsonArray.size() - 1) {
                sb.append(o.get("peopleNm"));
            } else if (i == 2) {
                sb.append(o.get("peopleNm")).append(", 이하 생략");
            } else {
                sb.append(o.get("peopleNm")).append(", ");
            }
        }
        return sb.isEmpty() ? "정보 없음" : sb.toString();
    }

    private String jsonArrayToStringCompanys(StringBuilder sb, JSONArray jsonArray) {
        for (int i = 0; i < jsonArray.size(); i++) {
            if (i >= 3) {
                break;
            }
            JSONObject o = mapper.convertValue(jsonArray.get(i), JSONObject.class);
            if (i == jsonArray.size() - 1) {
                sb.append(o.get("companyNm"));
            } else if (i == 2) {
                sb.append(o.get("companyNm")).append(", 이하 생략");
            } else {
                sb.append(o.get("companyNm")).append(", ");
            }
        }
        return sb.isEmpty() ? "정보 없음" : sb.toString();
    }
}
