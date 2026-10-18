package com.greencity.api.clients;

import com.greencity.api.models.econews.EcoNewsDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * GreenCity backend: {@code /eco-news/*}.
 */
public class EcoNewsClient extends BaseClient {

    private static final String ECO_NEWS = "/eco-news";

    public EcoNewsClient(String apiBaseUrl) {
        super(apiBaseUrl, ContentType.JSON);
    }

    public EcoNewsClient(String apiBaseUrl, String accessToken) {
        super(apiBaseUrl, ContentType.JSON);
        setToken(accessToken);
    }

    public Response getAll(int page, int size) {
        return preparedRequest()
                .queryParam("page", page)
                .queryParam("size", size)
                .get(ECO_NEWS);
    }

    public Response getById(long ecoNewsId) {
        return preparedRequest()
                .get(ECO_NEWS + "/{ecoNewsId}", ecoNewsId);
    }

    public EcoNewsDto getByIdAsDto(long ecoNewsId) {
        return getById(ecoNewsId)
                .then()
                .statusCode(200)
                .extract()
                .as(EcoNewsDto.class);
    }

    public Response delete(long ecoNewsId) {
        return preparedRequest()
                .delete(ECO_NEWS + "/{ecoNewsId}", ecoNewsId);
    }
}
