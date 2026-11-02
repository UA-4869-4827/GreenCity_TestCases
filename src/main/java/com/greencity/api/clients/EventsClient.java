package com.greencity.api.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.greencity.api.models.events.AddEventRequestDto;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * GreenCity backend: {@code /events/*}.
 */
public class EventsClient extends BaseClient {

    private static final String EVENTS = "/events";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public EventsClient(String apiBaseUrl) {
        super(apiBaseUrl, ContentType.JSON);
    }

    public EventsClient(String apiBaseUrl, String accessToken) {
        this(apiBaseUrl);
        setToken(accessToken);
    }

    @Step("Get all events: page {page}, size {size}")
    public Response getAll(int page, int size) {
        return preparedRequest()
                .queryParam("page", page)
                .queryParam("size", size)
                .get(EVENTS);
    }

    @Step("Get event by ID {eventId}")
    public Response getById(long eventId) {
        return preparedRequest()
                .get(EVENTS + "/{eventId}", eventId);
    }

    @Step("Create event")
    public Response create(String addEventDtoRequest) {
        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEventDtoRequest", addEventDtoRequest, "application/json")
                .post(EVENTS);
    }

    @Step("Create event using createV2 endpoint")
    public Response createV2(AddEventRequestDto event) {
        String eventJson;
        try {
            eventJson = OBJECT_MAPPER.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Cannot serialize event request", e);
        }
        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("addEventDtoRequest", eventJson, "application/json")
                .post(EVENTS + "/createV2");
    }

    @Step("Delete event with ID {eventId}")
    public Response delete(long eventId) {
        return preparedRequest()
                .delete(EVENTS + "/{eventId}", eventId);
    }
}
