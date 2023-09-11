package com.zerobase.Oauth.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zerobase.domain.type.OAuthProvider;
import lombok.Getter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
public class NaverUserInfoResponse implements OAuthUserInfoResponse {
    @JsonProperty("response")
    private Response response;

    @Getter
    static class Response {
        private String email;
        private String name;
        private String birthday;
        private String birthyear;
        private String mobile;
    }

    @Override
    public String getEmail() {
        return response.email;
    }

    @Override
    public String getName() {
        return response.name;
    }

    public LocalDate getBirth() {
        String birth = response.birthyear + response.birthday.replaceAll("-", "");
        return LocalDate.parse(birth, DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public String getPhone() {
        return response.mobile;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.NAVER;
    }
}
