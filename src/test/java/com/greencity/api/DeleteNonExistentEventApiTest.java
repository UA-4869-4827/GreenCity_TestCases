package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteNonExistentEventApiTest extends AuthenticatedApiTestRunner {

    private static final long NON_EXISTENT_EVENT_ID = Long.MAX_VALUE;

    @Test
    @DisplayName("DELETE /events/{eventId} returns 404 for a non-existent event")
    void deleteNonExistentEventReturns404() {
        EventsClient events = new EventsClient(testValueProvider.getBaseAPIUrl(), accessToken);
        Response response = events.delete(NON_EXISTENT_EVENT_ID);

        assertEquals(404, response.statusCode(), response.asPrettyString());
    }
}
