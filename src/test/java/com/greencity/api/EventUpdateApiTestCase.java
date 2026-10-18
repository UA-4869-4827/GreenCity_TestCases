package com.greencity.api;

import com.greencity.api.clients.AuthClient;
import com.greencity.api.clients.EventsClient;
import com.greencity.api.models.events.EventDto;
import com.greencity.api.models.events.UpdateEventRequestDto;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import com.greencity.api.utils.EventTestData;
import com.greencity.config.AppConfig;
import com.greencity.utils.TestValueProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class EventUpdateApiTestCase extends AuthenticatedApiTestRunner {

    private TestValueProvider provider;
    private EventsClient organizerClient;
    private EventsClient guestClient;
    private final List<Long> createdEventIds = new ArrayList<>();

    @BeforeEach
    void initClients() {
        provider = new TestValueProvider();
        organizerClient = new EventsClient(provider.getBaseAPIUrl(), accessToken);
        guestClient = new EventsClient(provider.getBaseAPIUrl());
    }

    @AfterEach
    void cleanUp() {
        createdEventIds.forEach(organizerClient::delete);
        createdEventIds.clear();
    }

    @Test
    @DisplayName("[Update Event][API] Organizer successfully updates event")
    void organizerUpdatesEventSuccessfully() {
        long eventId = createEventAsOrganizer();
        UpdateEventRequestDto update = EventTestData.validUpdate(eventId);

        Response updateResponse = organizerClient.update(eventId, update);
        assertEquals(200, updateResponse.statusCode(),
                "Unexpected status for PUT /events/" + eventId
                        + ". Body: " + updateResponse.asString());

        EventDto updated = getEvent(eventId);
        String expectedDescription = stripHtml(update.getDescription());
        assertAll("Event reflects the update",
                () -> assertEquals(eventId, updated.getId(), "Different event id"),
                () -> assertEquals(update.getTitle(), updated.getTitle(), "Title not updated"),
                () -> assertEquals(expectedDescription, updated.getDescription(),
                        "Description not updated"));
    }

    @Test
    @DisplayName("[Update Event][API] Other user cannot update event")
    void otherUserCannotUpdateEvent() {
        AppConfig config = AppConfig.get();
        String emailB = config.get("user2.email", "");
        String passwordB = config.get("user2.password", "");
        assumeTrue(!emailB.isBlank() && !passwordB.isBlank(),
                "user2.email / user2.password are not configured");

        long eventId = createEventAsOrganizer();
        EventDto original = getEvent(eventId);

        String tokenB = new AuthClient(provider.getBaseUserApiUrl())
                .signIn(emailB, passwordB)
                .getAccessToken();
        EventsClient otherUserClient = new EventsClient(provider.getBaseAPIUrl(), tokenB);

        Response response = otherUserClient.update(eventId, EventTestData.validUpdate(eventId));
        assertEquals(403, response.statusCode(),
                "Unexpected status for PUT by another user. Body: " + response.asString());

        assertEventUnchanged(eventId, original);
    }

    @Test
    @DisplayName("[Update Event][API] Unauthorized update returns 401")
    void unauthorizedUpdateReturnsUnauthorized() {
        long eventId = createEventAsOrganizer();
        EventDto original = getEvent(eventId);

        Response response = guestClient.update(eventId, EventTestData.validUpdate(eventId));
        assertEquals(401, response.statusCode(),
                "Unexpected status for PUT without token. Body: " + response.asString());

        assertEventUnchanged(eventId, original);
    }

    // ================= helpers =================
    private long createEventAsOrganizer() {
        Response response = organizerClient.create(EventTestData.validEvent());
        assertEquals(201, response.statusCode(),
                "Precondition failed: cannot create event. Body: " + response.asString());
        long eventId = response.jsonPath().getLong("id");
        createdEventIds.add(eventId);
        return eventId;
    }

    private EventDto getEvent(long eventId) {
        Response response = organizerClient.getById(eventId);
        assertEquals(200, response.statusCode(), "Unexpected status for GET /events/" + eventId);
        return response.as(EventDto.class);
    }

    private void assertEventUnchanged(long eventId, EventDto original) {
        EventDto after = getEvent(eventId);
        assertAll("Original data unchanged",
                () -> assertEquals(original.getTitle(), after.getTitle(), "Title changed"),
                () -> assertEquals(original.getDescription(), after.getDescription(),
                        "Description changed"));
    }

    private static String stripHtml(String html) {
        return html.replaceAll("<[^>]*>", "");
    }
}