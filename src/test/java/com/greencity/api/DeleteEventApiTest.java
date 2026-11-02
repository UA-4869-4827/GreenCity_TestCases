package com.greencity.api;

import com.greencity.api.testRunners.ApiTestRunner;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("API")
@Feature("Events")
@DisplayName("Event deletion API tests")
class DeleteEventApiTest extends ApiTestRunner {

    private static final int NON_EXISTENT_EVENT_ID = 999999;

    @Test
    @Story("Guest cannot delete an event")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("DELETE /events/{eventId} without authorization returns 401")
    @Description("""
            Verify that an unauthenticated request to delete an event
            is rejected.
            Expected result: the API returns HTTP 401 Unauthorized.
            """)
    @Issue("155")
    void deleteEventWithoutAuthorizationReturns401() {
        Response response = eventsClient.delete(NON_EXISTENT_EVENT_ID);

        assertEquals(401, response.statusCode());
    }
}