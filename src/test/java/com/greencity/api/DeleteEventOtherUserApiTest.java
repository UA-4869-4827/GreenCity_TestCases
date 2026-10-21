
package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.models.auth.SignInResponse;
import com.greencity.api.support.ApiAuth;
import com.greencity.api.testRunners.ApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeleteEventOtherUserApiTest extends ApiTestRunner {

    private String createEventRequest() {
        return """
                {
                  "title": "API test event for delete authorization",
                  "description": "Test event created by its organizer",
                  "open": true,
                  "datesLocations": [
                    {
                      "startDate": "2026-10-25T15:00:00Z",
                      "finishDate": "2026-10-25T17:00:00Z",
                      "onlineLink": "https://example.com"
                    }
                  ],
                  "tags": ["Social"]
                }
                """;
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

        EventsClient organizerClient = new EventsClient(testValueProvider.getBaseAPIUrl());
        organizerClient.setToken(organizer.getAccessToken());

        EventsClient otherUserClient = new EventsClient(testValueProvider.getBaseAPIUrl());
        otherUserClient.setToken(otherUser.getAccessToken());

       
        String requestBody = createEventRequest();

        Response createResponse = organizerClient.create(requestBody);

        assertEquals(
                201,
                createResponse.statusCode(),
                "Could not create the test event. Response: " + createResponse.asString());

        Long eventId = createResponse.jsonPath().getLong("id");
        assertNotNull(eventId, "Create event response did not contain an event ID");

        Response deleteResponse = otherUserClient.delete(eventId);
        assertEquals(
                403,
                deleteResponse.statusCode(),
                "A different user should not be allowed to delete the event");

        Response getResponse = organizerClient.getById(eventId);
        assertEquals(
                200,
                getResponse.statusCode(),
                "The event should still exist after the forbidden delete attempt");
        assertEquals(
                eventId.longValue(),
                getResponse.jsonPath().getLong("id"),
                "The returned event ID should match the created event");
    }
}
