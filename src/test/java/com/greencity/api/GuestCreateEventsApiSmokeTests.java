package com.greencity.api;

import com.greencity.api.testRunners.ApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuestCreateEventsApiSmokeTests extends ApiTestRunner {

    private String createEventRequest() {
        return """
                {
                  "title": "Test event",
                  "description": "Test event description",
                  "datesLocations": [],
                  "tags": [],
                  "open": true
                }
                """;
    }

    @Test
    @DisplayName("POST /events without authorization returns 401")
    void createEventWithoutAuthorizationReturnsUnauthorized() {
        Response response = eventsClient.create(createEventRequest());
        assertEquals(401, response.statusCode());
    }
}

