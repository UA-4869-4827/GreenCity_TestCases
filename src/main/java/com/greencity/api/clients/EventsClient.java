package com.greencity.api.clients;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * GreenCity backend: {@code /events/*}.
 */
public class EventsClient extends BaseClient {

    private static final String EVENTS = "/events";

    public EventsClient(String apiBaseUrl) {
        super(apiBaseUrl, ContentType.JSON);
    }

    public Response getAll(int page, int size) {
        return preparedRequest()
                .queryParam("page", page)
                .queryParam("size", size)
                .get(EVENTS);
    }

    public Response getById(long eventId) {
        return preparedRequest()
                .get(EVENTS + "/{eventId}", eventId);
    }

    public Response delete(long eventId) {
        return preparedRequest()
                .delete(EVENTS + "/{eventId}", eventId);
    }
}