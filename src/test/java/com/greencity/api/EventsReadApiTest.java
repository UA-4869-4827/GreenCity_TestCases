package com.greencity.api;

import com.greencity.api.models.events.EventDto;
import com.greencity.api.testRunners.ApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class EventsReadApiTest extends ApiTestRunner {

    private static final long NON_EXISTING_EVENT_ID = 999999L;

    @Test
    @DisplayName("GET /events returns 200 with a page of events")
    void getEventsListReturnsOk() {
        Response response = eventsClient.getAll(0, 10);

        assertEquals(200, response.statusCode(), "Unexpected status for GET /events");

        assertAll("Page structure",
                () -> assertNotNull(response.jsonPath().getList("page"),
                        "Response has no 'page' array"),
                () -> assertNotNull(response.jsonPath().get("totalElements"),
                        "Response has no 'totalElements'"),
                () -> assertNotNull(response.jsonPath().get("totalPages"),
                        "Response has no 'totalPages'"),
                () -> assertEquals(0, response.jsonPath().getInt("currentPage"),
                        "Requested page 0, got a different currentPage"));
    }

    @Test
    @DisplayName("GET /events/{id} returns 200 for an existing event")
    void getEventByIdReturnsOk() {
        Long existingEventId = eventsClient.getAll(0, 1)
                .jsonPath()
                .getLong("page[0].id");

        assumeTrue(existingEventId != null,
                "No events on the environment to read by id");

        Response response = eventsClient.getById(existingEventId);

        assertEquals(200, response.statusCode(),
                "Unexpected status for GET /events/" + existingEventId);

        EventDto event = response.as(EventDto.class);

        assertAll("Event fields",
                () -> assertEquals(existingEventId, event.getId(),
                        "Response contains a different event id"),
                () -> assertNotNull(event.getTitle(), "Event has no title"),
                () -> assertFalse(event.getTitle().isBlank(), "Event title is empty"));
    }

    @Test
    @DisplayName("GET /events/{id} returns 404 for a non-existent event")
    void getNonExistingEventReturnsNotFound() {
        Response response = eventsClient.getById(NON_EXISTING_EVENT_ID);
        assertEquals(404, response.statusCode(), "Unexpected status for GET /events/" + NON_EXISTING_EVENT_ID);
    }

}
