package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.models.events.AddEventRequestDto;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DeleteEventAuthorizedApiTest extends AuthenticatedApiTestRunner {

    private static final String DESCRIPTION = "Event created for the organizer delete API test.";

    private Long createdEventId;

    @AfterEach
    void deleteEventIfLeftOver() {
        if (createdEventId != null) {
            organizerClient().delete(createdEventId);
            createdEventId = null;
        }
    }

    @Test
    @DisplayName("DELETE /events/{eventId} returns 200 and the event is gone")
    void organizerSuccessfullyDeletesEvent() {
        EventsClient events = organizerClient();
        Response createResponse = events.createV2(validEvent());
        assertEquals(201, createResponse.statusCode(), createResponse.asPrettyString());

        createdEventId = createResponse.jsonPath().getLong("id");
        assertNotNull(createdEventId, "Created event has no id");

        Response deleteResponse = events.delete(createdEventId);
        assertEquals(200, deleteResponse.statusCode(), deleteResponse.asPrettyString());

        Response getResponse = events.getById(createdEventId);
        assertEquals(404, getResponse.statusCode(),
                "Deleted event is still available by id " + createdEventId);
        createdEventId = null;
    }

    private EventsClient organizerClient() {
        return new EventsClient(testValueProvider.getBaseAPIUrl(), accessToken);
    }

    private static AddEventRequestDto validEvent() {
        AddEventRequestDto.DateLocationDto dateLocation = AddEventRequestDto.DateLocationDto.builder()
                .startDate("2027-06-15T10:00:00Z")
                .finishDate("2027-06-15T12:00:00Z")
                .onlineLink("https://example.com/event")
                .build();
        return AddEventRequestDto.builder()
                .title("API delete " + UUID.randomUUID())
                .description(DESCRIPTION)
                .open(true)
                .datesLocations(List.of(dateLocation))
                .tags(List.of("Social"))
                .build();
    }
}
