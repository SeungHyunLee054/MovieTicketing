package com.zerobase.social.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RequestBodyKakao {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
}
