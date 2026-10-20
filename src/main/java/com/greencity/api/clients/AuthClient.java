package com.greencity.api.clients;

import com.greencity.api.models.auth.SignInRequest;
import com.greencity.api.models.auth.SignInResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * GreenCity User service: {@code /ownSecurity/*}.
 */
public class AuthClient extends BaseClient {

    private static final String OWN_SECURITY = "/ownSecurity";
    private static final String PROJECT_GREENCITY = "GREENCITY";

    public AuthClient(String userApiBaseUrl) {
        super(userApiBaseUrl, ContentType.JSON);
    }

    public Response signInRaw(String email, String password) {
        SignInRequest body = SignInRequest.builder()
                .email(email)
                .password(password)
                .projectName(PROJECT_GREENCITY)
                .build();
        return preparedRequest()
                .body(body)
                .post(OWN_SECURITY + "/signIn");
    }

    public SignInResponse signIn(String email, String password) {
        return signInRaw(email, password)
                .then()
                .statusCode(200)
                .extract()
                .as(SignInResponse.class);
    }
}
