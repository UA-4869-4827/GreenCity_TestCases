package com.greencity.api;

import com.greencity.api.testRunners.ApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteEventApiTest extends ApiTestRunner {

    private static final int NON_EXISTENT_EVENT_ID = 999999;

    @Test
    @DisplayName("DELETE /events/{eventId} without authorization returns 401")
    void deleteEventWithoutAuthorizationReturns401() {
        Response response = eventsClient.delete(NON_EXISTENT_EVENT_ID);

        assertEquals(401, response.statusCode());
    }
}