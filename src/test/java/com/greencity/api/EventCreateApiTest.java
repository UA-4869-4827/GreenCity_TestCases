package com.greencity.api;

import com.greencity.api.clients.EventsClient;
import com.greencity.api.models.events.AddEventRequestDto;
import com.greencity.api.models.events.EventDto;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import com.greencity.api.utils.EventTestData;
import com.greencity.utils.TestValueProvider;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EventCreateApiTest extends AuthenticatedApiTestRunner {

    private EventsClient authorizedEventsClient;
    private final List<Long> createdEventIds = new ArrayList<>();

    @BeforeEach
    void initClient() {
        authorizedEventsClient = new EventsClient(
                new TestValueProvider().getBaseAPIUrl(), accessToken);
    }

    @AfterEach
    void cleanUp() {
        createdEventIds.forEach(authorizedEventsClient::delete);
        createdEventIds.clear();
    }

    @Test
    @DisplayName("[Create Event][API] Author successfully creates event")
    void authorCreatesEventSuccessfully() {
        AddEventRequestDto request = EventTestData.validEvent();

        Response createResponse = authorizedEventsClient.create(request);
        assertEquals(201, createResponse.statusCode(),
                "Unexpected status for POST /events. Body: " + createResponse.asString());

        Long eventId = createResponse.jsonPath().getLong("id");
        assertNotNull(eventId, "Response has no event id");
        createdEventIds.add(eventId);

        Response getResponse = authorizedEventsClient.getById(eventId);
        assertEquals(200, getResponse.statusCode(),
                "Unexpected status for GET /events/" + eventId);

        EventDto created = getResponse.as(EventDto.class);
        String expectedDescription = request.getDescription().replaceAll("<[^>]*>", "");
        assertAll("Created event matches the request",
                () -> assertEquals(eventId, created.getId(), "Different event id"),
                () -> assertEquals(request.getTitle(), created.getTitle(), "Title mismatch"),
                () -> assertEquals(expectedDescription, created.getDescription(),
                        "Description mismatch"));
    }

    static Stream<Arguments> invalidPayloads() {
        AddEventRequestDto emptyTitle = EventTestData.validEvent();
        emptyTitle.setTitle("");

        AddEventRequestDto missingTitle = EventTestData.validEvent();
        missingTitle.setTitle(null);

        AddEventRequestDto missingDatesLocations = EventTestData.validEvent();
        missingDatesLocations.setDatesLocations(null);

        AddEventRequestDto missingTags = EventTestData.validEvent();
        missingTags.setTags(null);

        AddEventRequestDto shortDescription = EventTestData.validEvent();
        shortDescription.setDescription("<p>a</p>");

        return Stream.of(
                Arguments.of("empty title", emptyTitle),
                Arguments.of("missing title", missingTitle),
                Arguments.of("missing datesLocations", missingDatesLocations),
                Arguments.of("missing tags", missingTags),
                Arguments.of("description shorter than minimum", shortDescription));
    }

    @ParameterizedTest(name = "[Create Event][API] Invalid payload returns 400: {0}")
    @MethodSource("invalidPayloads")
    void invalidPayloadReturnsBadRequest(String caseName, AddEventRequestDto payload) {
        Response response = authorizedEventsClient.create(payload);

        if (response.statusCode() == 201) {
            createdEventIds.add(response.jsonPath().getLong("id"));
        }

        assertEquals(400, response.statusCode(),
                "Case '" + caseName + "' returned unexpected status. Body: " + response.asString());
    }
}