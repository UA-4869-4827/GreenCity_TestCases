package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("API")
@Feature("Events")
@DisplayName("Event deletion API tests")
public class DeleteNonExistentEventApiTest extends AuthenticatedApiTestRunner {

    private static final long NON_EXISTENT_EVENT_ID = Long.MAX_VALUE;

    @Test
    @Story("Organizer cannot delete a non-existent event")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("DELETE /events/{eventId} returns 404 for a non-existent event")
    @Description("""
            Verify that an authenticated request to delete an event
            with a non-existent ID is handled correctly.
            Expected result: the API returns HTTP 404 Not Found.
            """)
    @Issue("156")
    void deleteNonExistentEventReturns404() {
        EventsClient events = new EventsClient(testValueProvider.getBaseAPIUrl(), accessToken);
        Response response = events.delete(NON_EXISTENT_EVENT_ID);

        assertEquals(404, response.statusCode(), response.asPrettyString());
    }
}
