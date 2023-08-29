package com.zerobase.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.domain.MovieDetailDto;
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

import java.util.List;
import java.util.Objects;

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

    private JsonNode getMovieJson(String page) {
        String strUrl = API_BASE_URL
                + API_MOVIES_URL
                + KEY
                + "&curPage=" + page
                + "&itemPerPage=100";

        JsonNode root = getResponse(strUrl);

        return root.get("movieListResult");
    }

    public List<Movie> getMovieList(String page) {
        JsonNode movieList = getMovieJson(page).get("movieList");

        try {
            return mapper.readValue(movieList.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            log.error("Json 정보를 가져오지 못했습니다.");
        }

        return null;
    }

    public MovieDetailDto getMovieDetail(String movieCd) {
        String strUrl = API_BASE_URL
                + API_MOVIE_DETAIL_URL
                + KEY
                + "&movieCd=" + movieCd;

        JsonNode root = getResponse(strUrl);

        JsonNode movieInfoResult = root.get("movieInfoResult");
        JsonNode movieInfo = movieInfoResult.get("movieInfo");

        try {
            return mapper.readValue(movieInfo.toString(), new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            log.error("Json 정보를 가져오지 못했습니다.");
        }

        return null;
    }

    private JsonNode getResponse(String strUrl) {
        ResponseEntity<String> response = restTemplate.getForEntity(strUrl, String.class);
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (JsonProcessingException e) {
            log.error("Json 정보를 가져오지 못했습니다.");
        }

        if (root == null) {
            throw new CustomException(ErrorCode.WRONG_URL);
        }

        return root;
    }

    public int totalCnt() {
        JsonNode totCnt = getMovieJson("1").get("totCnt");
        try {
            return mapper.readValue(totCnt.toString(), Integer.class);
        } catch (JsonProcessingException e) {
            log.error("Json 정보를 가져오지 못했습니다.");
        }

        return -1;
    }
}