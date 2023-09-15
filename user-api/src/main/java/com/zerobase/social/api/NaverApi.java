package com.zerobase.social.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zerobase.domain.type.OAuthProvider;
import com.zerobase.social.domain.RequestBodyNaver;
import com.zerobase.social.domain.SocialLoginParam;
import com.zerobase.social.domain.response.NaverTokenResponse;
import com.zerobase.social.domain.response.NaverUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class NaverApi implements SocialApi {
    private final RestTemplate restTemplate;
    @Value("${naver.auth.uri}")
    private String NAVER_AUTH_URI;
    @Value("${naver.api.uri}")
    private String NAVER_API_URI;
    @Value("${naver.redirect.uri}")
    private String REDIRECT_URI;
    @Value("${grant.type}")
    private String GRANT_TYPE;
    private final String CLIENT_ID = System.getProperty("naver_client_id");
    private final String CLIENT_SECRET = System.getProperty("naver_client_secret");

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String getToken(SocialLoginParam param) {
        String url = NAVER_AUTH_URI + "/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = param.makeBody();
        body.setAll(new ObjectMapper().convertValue(
                new RequestBodyNaver(GRANT_TYPE, CLIENT_ID, CLIENT_SECRET, REDIRECT_URI),
                new TypeReference<>() {
                }));

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        NaverTokenResponse response =
                restTemplate.postForObject(url, request, NaverTokenResponse.class);

        assert response != null;
        return response.getAccessToken();
    }

    @Override
    public NaverUserInfoResponse getUserInfo(String accessToken) {
        String url = NAVER_API_URI + "/v1/nid/me";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<?> request = new HttpEntity<>(httpHeaders);

        return restTemplate.postForObject(url, request, NaverUserInfoResponse.class);
    }
}
