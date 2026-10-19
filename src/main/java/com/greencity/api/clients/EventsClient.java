package com.greencity.api.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class EventsClient extends BaseClient {
    private static final String EVENTS = "/events";

    public EventsClient(String apiBaseUrl) {
        super(apiBaseUrl, ContentType.MULTIPART);
    }

    public Response create(String addEventDtoRequest) {
        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEventDtoRequest", addEventDtoRequest, "application/json")
                .post(EVENTS);
    }
}
