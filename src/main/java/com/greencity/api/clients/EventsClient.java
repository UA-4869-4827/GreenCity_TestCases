package com.greencity.api.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greencity.api.models.events.AddEventRequestDto;
import com.greencity.api.models.events.UpdateEventRequestDto;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * GreenCity backend: {@code /events/*}.
 */
public class EventsClient extends BaseClient {

    private static final String EVENTS = "/events";
    private static final String EVENT_BY_ID = EVENTS + "/{eventId}";
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
                .get(EVENT_BY_ID, eventId);
    }

    public Response create(AddEventRequestDto dto) {
        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEventDtoRequest", toJson(dto), "application/json")
                .post(EVENTS);
    }

    public Response update(long eventId, UpdateEventRequestDto dto) {
        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("eventDto", toJson(dto), "application/json")
                .put(EVENT_BY_ID, eventId);
    }

    public Response delete(long eventId) {
        return preparedRequest()
                .delete(EVENT_BY_ID, eventId);
    }

    private static String toJson(Object payload) {
        try {
            return MAPPER.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Cannot serialize payload", e);
        }
    }
}