package com.greencity.api.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignInResponse {

    private Long userId;
    private String accessToken;
    private String refreshToken;
    private String name;
    private boolean ownRegistrations;
}
