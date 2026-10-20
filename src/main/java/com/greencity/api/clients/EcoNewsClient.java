package com.greencity.api.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greencity.api.models.econews.AddEcoNewsDtoRequest;
import com.greencity.api.models.econews.EcoNewsDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 * GreenCity backend: {@code /eco-news/*}.
 */
public class EcoNewsClient extends BaseClient {

    private static final String ECO_NEWS = "/eco-news";
    private static final String ADD_REQUEST_PART = "addEcoNewsDtoRequest";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

    /**
     * Creates eco news. Requires Authorization. Multipart part name:
     * {@code addEcoNewsDtoRequest} (JSON). Image part is optional.
     */
    public Response create(AddEcoNewsDtoRequest request) {
        String json;
        try {
            json = OBJECT_MAPPER.writeValueAsString(request);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize AddEcoNewsDtoRequest", e);
        }

        RequestSpecification spec = RestAssured.given()
                .baseUri(baseApiUrl)
                .accept(ContentType.JSON)
                .multiPart(ADD_REQUEST_PART, json, "application/json");
        if (token != null && !token.isBlank()) {
            spec.header("Authorization", bearer(token));
        }
        return spec.post(ECO_NEWS);
    }

    public EcoNewsDto createAsDto(AddEcoNewsDtoRequest request) {
        return create(request)
                .then()
                .statusCode(201)
                .extract()
                .as(EcoNewsDto.class);
    }

    public Response delete(long ecoNewsId) {
        return preparedRequest()
                .delete(ECO_NEWS + "/{ecoNewsId}", ecoNewsId);
    }
}
