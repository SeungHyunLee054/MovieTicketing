package com.zerobase.Oauth.api;

import com.zerobase.Oauth.domain.OAuthLoginParam;
import com.zerobase.Oauth.domain.response.NaverTokenResponse;
import com.zerobase.Oauth.domain.response.NaverUserInfoResponse;
import com.zerobase.domain.type.OAuthProvider;
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
public class NaverApi implements OauthApi {
    private final RestTemplate restTemplate;
    @Value("${naver.auth.uri}")
    private String NAVER_AUTH_URI;
    @Value("${naver.api.uri}")
    private String NAVER_API_URI;
    @Value("${naver.redirect.uri}")
    private String REDIRECT_URI;
    @Value("${grant.type}")
    private String GRANT_TYPE;

    private final String clientId = System.getProperty("naver_client_id");

    private final String clientSecret = System.getProperty("naver_client_secret");

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.NAVER;
    }

    @Override
    public String getToken(OAuthLoginParam param) {
        String url = NAVER_AUTH_URI + "/oauth2.0/token";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> body = param.makeBody();
        body.add("grant_type", GRANT_TYPE);
        body.add("client_id", clientId);
        body.add("client_secret", clientSecret);
        body.add("redirect_uri", REDIRECT_URI);

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

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);

        return restTemplate.postForObject(url, request, NaverUserInfoResponse.class);
    }
}
