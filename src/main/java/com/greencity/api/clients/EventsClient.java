package com.greencity.api.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greencity.api.models.events.AddEventRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * GreenCity backend: {@code /events/*}.
 */
public class EventsClient extends BaseClient {

    private static final String EVENTS = "/events";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EventsClient(String apiBaseUrl) {
        super(apiBaseUrl, ContentType.JSON);
    }

    public EventsClient(String apiBaseUrl, String accessToken) {
        super(apiBaseUrl, ContentType.JSON);
        setToken(accessToken);
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

    public Response create(AddEventRequestDto dto) {
        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEventDtoRequest", toJson(dto), "application/json")
                .post(EVENTS);
    }

    public Response delete(long eventId) {
        return preparedRequest()
                .delete(EVENTS + "/delete/{eventId}", eventId);
    }

    private static String toJson(Object payload) {
        try {
            return MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot serialize payload", e);
        }
    }
}