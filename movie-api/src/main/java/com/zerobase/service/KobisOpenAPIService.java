package com.zerobase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.domain.MovieDto;
import com.zerobase.domain.model.Movie;
import com.zerobase.exception.CustomException;
import com.zerobase.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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

    public JsonNode getMovieJson(String page) {
        String strUrl = API_BASE_URL
                + API_MOVIES_URL
                + KEY
                + "&curPage=" + page
                + "&itemPerPage=100";

        ResponseEntity<String> response = restTemplate.getForEntity(strUrl, String.class);
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {

        }

        if (root == null) {
            throw new CustomException(ErrorCode.WRONG_URL);
        }

        JsonNode movieListResult = root.get("movieListResult");

        return movieListResult;
    }

    public List<Movie> getMovieList(String page) {
        JsonNode movieList = getMovieJson(page).get("movieList");
        List list = mapper.convertValue(movieList, List.class);
        List<MovieDto> movieDtos = list.stream().map(x -> mapper.convertValue(x, MovieDto.class)).toList();
        List<Movie> movies = movieDtos.stream().map(MovieDto::from).toList();

        return movies;
    }

    public JSONObject getMovieDetail(String movieCd) {
        String strUrl = API_BASE_URL
                + API_MOVIE_DETAIL_URL
                + KEY
                + "&movieCd=" + movieCd;

        return restTemplate.getForEntity(strUrl, JSONObject.class).getBody();
    }

    public int totalCnt(JsonNode movieListResult) {
        return mapper.convertValue(movieListResult.get("totCnt"), Integer.class);
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
