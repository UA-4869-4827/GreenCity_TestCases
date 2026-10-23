package com.greencity.api;

import com.greencity.api.clients.AuthClient;
import com.greencity.api.clients.EventsClient;
import com.greencity.api.models.auth.SignInResponse;
import com.greencity.api.models.events.AddEventRequestDto;
import com.greencity.api.models.events.UpdateEventRequestDto;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Update Event API acceptance criteria, TC #150, #151, #152 and wrong-id check.
 * Each test creates and deletes its own event to avoid reliance on hard-coded IDs.
 */
public class UpdateEventApiTest extends AuthenticatedApiTestRunner {

    private static final String EVENT_TAG = "Social";
    private static final String ORIGINAL_DESCRIPTION =
            "Initial event description for the GreenCity API update test.";
    private static final String UPDATED_DESCRIPTION =
            "Updated event description for the GreenCity API update test.";

    private Long createdEventId;
    private EventsClient organizerClient;

    @AfterEach
    void deleteTestEvent() {
        if (createdEventId != null) {
            Response deleted = organizerClient.delete(createdEventId);
            assertEquals(200, deleted.statusCode(),
                    "Cleanup failed for event " + createdEventId + ": " + deleted.asPrettyString());
            createdEventId = null;
        }
    }

    @Test
    @DisplayName("#150: Organizer updates event (200), GET returns updated fields")
    void organizerSuccessfullyUpdatesEvent() {
        long eventId = createEventAsOrganizer();
        String newTitle = uniqueTitle("API edited event");
        UpdateEventRequestDto update = validUpdate(eventId, newTitle);

        Response updated = organizerClient.update(eventId, update);
        assertEquals(200, updated.statusCode(), updated.asPrettyString());
        assertAll("PUT returns updated event",
                () -> assertEquals(eventId, updated.jsonPath().getLong("id")),
                () -> assertEquals(newTitle, updated.jsonPath().getString("title")),
                () -> assertEquals(UPDATED_DESCRIPTION, updated.jsonPath().getString("description")));

        Response fetched = organizerClient.getById(eventId);
        assertEquals(200, fetched.statusCode(), fetched.asPrettyString());
        assertAll("GET reflects persisted changes",
                () -> assertEquals(eventId, fetched.jsonPath().getLong("id")),
                () -> assertEquals(newTitle, fetched.jsonPath().getString("title")),
                () -> assertEquals(UPDATED_DESCRIPTION, fetched.jsonPath().getString("description")));
    }

    @Test
    @DisplayName("#151: Non-organizer cannot update event (403), stored data unchanged")
    void otherUserCannotUpdateEvent() {
        String otherEmail = configuredValue("event.other.user.email", "EVENT_OTHER_USER_EMAIL");
        String otherPassword = configuredValue("event.other.user.password", "EVENT_OTHER_USER_PASSWORD");
        Assumptions.assumeTrue(otherEmail != null && otherPassword != null,
                "Set EVENT_OTHER_USER_EMAIL and EVENT_OTHER_USER_PASSWORD for a second regular user");
        assertNotEquals(testValueProvider.getUserEmail().trim().toLowerCase(),
                otherEmail.trim().toLowerCase(), "Second user must differ from organizer");

        long eventId = createEventAsOrganizer();
        Response before = organizerClient.getById(eventId);
        assertEquals(200, before.statusCode(), before.asPrettyString());
        String originalTitle = before.jsonPath().getString("title");
        String originalDescription = before.jsonPath().getString("description");

        SignInResponse otherUser = new AuthClient(testValueProvider.getBaseUserApiUrl())
                .signIn(otherEmail, otherPassword);
        assertNotNull(otherUser.getAccessToken(), "Second user must receive access token");
        assertNotEquals(signedInUser.getUserId(), otherUser.getUserId(),
                "A distinct account is required for the 403 authorization test");
        EventsClient otherClient = new EventsClient(
                testValueProvider.getBaseAPIUrl(), otherUser.getAccessToken());

        Response forbidden = otherClient.update(eventId,
                validUpdate(eventId, uniqueTitle("Not allowed change")));
        assertEquals(403, forbidden.statusCode(), forbidden.asPrettyString());

        assertEventUnchanged(eventId, originalTitle, originalDescription);
    }

    @Test
    @DisplayName("#152: Update without Authorization returns 401 and changes nothing")
    void updateWithoutTokenReturns401() {
        long eventId = createEventAsOrganizer();
        Response before = organizerClient.getById(eventId);
        assertEquals(200, before.statusCode(), before.asPrettyString());
        String originalTitle = before.jsonPath().getString("title");
        String originalDescription = before.jsonPath().getString("description");

        EventsClient guestClient = new EventsClient(testValueProvider.getBaseAPIUrl());
        Response unauthorized = guestClient.update(eventId,
                validUpdate(eventId, uniqueTitle("No token change")));
        assertEquals(401, unauthorized.statusCode(), unauthorized.asPrettyString());

        assertEventUnchanged(eventId, originalTitle, originalDescription);
    }

    @Test
    @DisplayName("AC4: Path eventId != body id is rejected with 400")
    void mismatchingPathAndBodyIdReturns400() {
        long eventId = createEventAsOrganizer();
        Response before = organizerClient.getById(eventId);
        assertEquals(200, before.statusCode(), before.asPrettyString());
        String originalTitle = before.jsonPath().getString("title");
        String originalDescription = before.jsonPath().getString("description");

        UpdateEventRequestDto request = validUpdate(eventId + 1,
                uniqueTitle("Wrong id change"));
        Response badRequest = organizerClient.update(eventId, request);
        assertEquals(400, badRequest.statusCode(), badRequest.asPrettyString());

        assertEventUnchanged(eventId, originalTitle, originalDescription);
    }

    private long createEventAsOrganizer() {
        organizerClient = new EventsClient(testValueProvider.getBaseAPIUrl(), accessToken);
        AddEventRequestDto create = AddEventRequestDto.builder()
                .title(uniqueTitle("API original event"))
                .description(ORIGINAL_DESCRIPTION)
                .open(true)
                .tags(List.of(EVENT_TAG))
                .datesLocations(eventDates())
                .build();

        Response created = organizerClient.createV2(create);
        assertEquals(201, created.statusCode(),
                "Cannot prepare an organizer-owned event: " + created.asPrettyString());
        long eventId = created.jsonPath().getLong("id");
        assertTrue(eventId > 0, "New event must have a positive id");
        createdEventId = eventId;
        return eventId;
    }

    private UpdateEventRequestDto validUpdate(long eventId, String title) {
        return UpdateEventRequestDto.builder()
                .id(eventId)
                .title(title)
                .description(UPDATED_DESCRIPTION)
                .open(true)
                .tags(List.of(EVENT_TAG))
                .datesLocations(eventDates())
                .build();
    }

    private void assertEventUnchanged(long eventId, String title, String description) {
        Response fetched = organizerClient.getById(eventId);
        assertEquals(200, fetched.statusCode(), fetched.asPrettyString());
        assertAll("Rejected PUT must not modify persisted event",
                () -> assertEquals(title, fetched.jsonPath().getString("title")),
                () -> assertEquals(description, fetched.jsonPath().getString("description")));
    }

    private static List<AddEventRequestDto.DateLocationDto> eventDates() {
        ZonedDateTime start = ZonedDateTime.now(ZoneOffset.UTC)
                .plusDays(30)
                .withHour(13)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        return List.of(AddEventRequestDto.DateLocationDto.builder()
                .startDate(start.toString())
                .finishDate(start.plusHours(2).toString())
                .onlineLink("https://example.org/greencity-api-event")
                .build());
    }

    private static String uniqueTitle(String prefix) {
        return prefix + " " + UUID.randomUUID();
    }

    private static String configuredValue(String property, String environment) {
        String value = System.getProperty(property);
        if (value == null || value.isBlank()) {
            value = System.getenv(environment);
        }
        return value == null || value.isBlank() ? null : value;
    }
}
