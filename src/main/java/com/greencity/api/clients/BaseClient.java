package com.greencity.api.clients;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Getter;
import lombok.Setter;

/**
 * Shared Rest Assured request setup for GreenCity API clients.
 * Subclasses set resource paths and call {@link #preparedRequest()}.
 */
public class BaseClient {

    protected final String baseApiUrl;
    protected final ContentType contentType;

    @Getter
    @Setter
    protected String token;

    public BaseClient(String baseUrl) {
        this.baseApiUrl = normalizeBaseUrl(baseUrl);
        this.contentType = ContentType.JSON;
    }

    public BaseClient(String baseUrl, ContentType contentType) {
        this.baseApiUrl = normalizeBaseUrl(baseUrl);
        this.contentType = contentType;
    }

    public BaseClient(String baseUrl, String contentType) {
        this.baseApiUrl = normalizeBaseUrl(baseUrl);
        this.contentType = ContentType.valueOf(contentType);
    }

    protected RequestSpecification preparedRequest() {
        RequestSpecification request = RestAssured.given()
                .baseUri(baseApiUrl)
                .accept(ContentType.JSON)
                .contentType(contentType);
        if (token != null && !token.isBlank()) {
            request.header("Authorization", bearer(token));
        }
        return request;
    }

    protected static String bearer(String accessToken) {
        if (accessToken.startsWith("Bearer ")) {
            return accessToken;
        }
        return "Bearer " + accessToken;
    }

    private static String normalizeBaseUrl(String baseUrl) {
        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalArgumentException("API base URL must not be blank");
        }
        return baseUrl.endsWith("/") ? baseUrl.substring(0, baseUrl.length() - 1) : baseUrl;
    }
}
