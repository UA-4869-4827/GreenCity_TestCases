package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.models.auth.SignInResponse;
import com.greencity.api.support.ApiAuth;
import com.greencity.api.testRunners.ApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeleteEventOtherUserApiTest extends ApiTestRunner {

    private Long createdEventId;
    private EventsClient organizerClient;

    @AfterEach
    void deleteCreatedEvent() {
        if (createdEventId != null && organizerClient != null) {
            organizerClient.delete(createdEventId);
            createdEventId = null;
        }
    }

    @Test
    @DisplayName("Another user cannot delete an organizer's event")
    void otherUserCannotDeleteOrganizerEvent() {
        Assumptions.assumeTrue(
                testValueProvider.hasConfiguredUserCredentials(),
                "Configure user.email and user.password for organizer A");
        Assumptions.assumeTrue(
                testValueProvider.hasConfiguredSecondUserCredentials(),
                "Configure second.user.email and second.user.password for user B");

        SignInResponse organizer = ApiAuth.signInAsConfiguredUser(testValueProvider);
        SignInResponse otherUser = ApiAuth.signInAsSecondUser(testValueProvider);

        assertNotNull(organizer.getAccessToken(), "Organizer A has no access token");
        assertNotNull(otherUser.getAccessToken(), "User B has no access token");

        organizerClient = new EventsClient(testValueProvider.getBaseAPIUrl());
        organizerClient.setToken(organizer.getAccessToken());

        EventsClient otherUserClient = new EventsClient(testValueProvider.getBaseAPIUrl());
        otherUserClient.setToken(otherUser.getAccessToken());

        Response createResponse = organizerClient.create(createEventRequest());

        assertEquals(
                201,
                createResponse.statusCode(),
                "Could not create the test event. Response: " + createResponse.asString());

        createdEventId = createResponse.jsonPath().getLong("id");
        assertNotNull(createdEventId, "Create event response did not contain an event ID");

        Response deleteResponse = otherUserClient.delete(createdEventId);
        assertEquals(
                403,
                deleteResponse.statusCode(),
                "A different user should not be allowed to delete the event");

        Response getResponse = organizerClient.getById(createdEventId);
        assertEquals(
                200,
                getResponse.statusCode(),
                "The event should still exist after the forbidden delete attempt");
        assertEquals(
                createdEventId.longValue(),
                getResponse.jsonPath().getLong("id"),
                "The returned event ID should match the created event");
    }

    private String createEventRequest() {
        ZonedDateTime start = ZonedDateTime.now(ZoneOffset.UTC).plusDays(30).withNano(0);
        String startDate = DateTimeFormatter.ISO_INSTANT.format(start.toInstant());
        String finishDate = DateTimeFormatter.ISO_INSTANT.format(start.plusHours(2).toInstant());
        return """
                {
                  "title": "API delete authorization %s",
                  "description": "Test event created by its organizer",
                  "open": true,
                  "datesLocations": [
                    {
                      "startDate": "%s",
                      "finishDate": "%s",
                      "onlineLink": "https://example.com"
                    }
                  ],
                  "tags": ["Social"]
                }
                """.formatted(UUID.randomUUID(), startDate, finishDate);
    }
}
