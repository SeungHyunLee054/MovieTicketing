package com.zerobase.service;

import com.zerobase.domain.model.Movie;
import com.zerobase.domain.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KobisOpenAPIService {
    private final MovieRepository movieRepository;
    private final String API_URL = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/";
    private final String KEY = System.getProperty("apiKey");

    public void getMoviesFromApi() {
        int totalPage = totalCnt(getMoviesString("1"));
        int pageEnd = totalPage / 100;
        String curPage;
        for (int i = 1; i <= pageEnd + 1; i++) {
            curPage = String.valueOf(i);
            String moviesString = getMoviesString(curPage);
            movieRepository.saveAll(parseMovie(moviesString));
        }
    }

    private String getMoviesString(String page) {
        String strUrl = API_URL
                + "searchMovieList.json?key="
                + KEY
                + "&curPage=" + page
                + "&itemPerPage=100";

        try {
            URL url = new URL(strUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            BufferedReader br;

            if (responseCode == 200) {
                br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream())
                );
            } else {
                br = new BufferedReader(
                        new InputStreamReader(connection.getErrorStream())
                );
            }
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();

            return response.toString();
        } catch (Exception e) {
            return "failed to get Movie data";
        }
    }

    private List<Movie> parseMovie(String jsonStr) {
        List<Movie> movies = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject movieListResult = (JSONObject) jsonObject.get("movieListResult");
        JSONArray jsonArray = (JSONArray) movieListResult.get("movieList");
        for (Object value : jsonArray) {
            JSONObject object = (JSONObject) value;
            if (movieRepository.findByMovieCd((String) object.get("movieCd")).isPresent()) {
                continue;
            }
            JSONArray directors = (JSONArray) object.get("directors");
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < directors.size(); j++) {
                if (j >= 3) {
                    break;
                }
                JSONObject o = (JSONObject) directors.get(j);
                if (j == directors.size() - 1 || j == 2) {
                    sb.append(o.get("peopleNm"));
                } else {
                    sb.append(o.get("peopleNm")).append(", ");
                }
            }
            String director = sb.length() == 0 ? "감독명 없음" : sb.toString();
            JSONArray companys = (JSONArray) object.get("companys");
            sb = new StringBuilder();
            for (int j = 0; j < companys.size(); j++) {
                if (j >= 3) {
                    break;
                }
                JSONObject o = (JSONObject) companys.get(j);
                if (j == companys.size() - 1 || j == 2) {
                    sb.append(o.get("companyNm"));
                } else {
                    sb.append(o.get("companyNm")).append(", ");
                }
            }
            String company = sb.length() == 0 ? "회사명 없음" : sb.toString();
            movies.add(Movie.builder()
                    .movieCd((String) object.get("movieCd"))
                    .movieNm((String) object.get("movieNm"))
                    .movieNmEn((String) object.get("movieNmEn"))
                    .prdtYear((String) object.get("prdtYear"))
                    .openDt((String) object.get("openDt"))
                    .typeNm((String) object.get("typeNm"))
                    .prdtStatNm((String) object.get("prdtStatNm"))
                    .nationAlt((String) object.get("nationAlt"))
                    .genreAlt((String) object.get("genreAlt"))
                    .directorNm(director)
                    .companyNm(company)
                    .build());
        }
        return movies;
    }

    private int totalCnt(String jsonStr) {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject movieResult = (JSONObject) jsonObject.get("movieListResult");
        return Integer.parseInt(String.valueOf(movieResult.get("totCnt")));
    }
}
