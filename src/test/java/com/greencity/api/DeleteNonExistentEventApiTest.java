package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteNonExistentEventApiTest extends AuthenticatedApiTestRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteNonExistentEventApiTest.class);
    private static final long NON_EXISTENT_EVENT_ID = Long.MAX_VALUE;

    @Test
    @DisplayName("DELETE /events/{eventId} returns 404 for a non-existent event")
    void deleteNonExistentEventReturns404() {
        LOG.info("DELETE /events/{} for a non-existent event", NON_EXISTENT_EVENT_ID);
        LOG.debug("Prepared an authorized events client");
        EventsClient events = new EventsClient(testValueProvider.getBaseAPIUrl(), accessToken);
        Response response = events.delete(NON_EXISTENT_EVENT_ID);
        LOG.info("DELETE /events/{} returned {}", NON_EXISTENT_EVENT_ID, response.statusCode());

        assertEquals(404, response.statusCode(), response.asPrettyString());
    }
}
