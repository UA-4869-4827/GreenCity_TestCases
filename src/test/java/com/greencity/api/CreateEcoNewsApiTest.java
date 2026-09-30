package com.greencity.api;

import com.greencity.api.clients.EcoNewsClient;
import com.greencity.api.models.econews.AddEcoNewsDtoRequest;
import com.greencity.api.models.econews.EcoNewsDto;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Reference API tests for creating eco news ({@code POST /eco-news}).
 * <p>
 * Pattern for students:
 * <ol>
 *   <li>extend {@link AuthenticatedApiTestRunner} for JWT</li>
 *   <li>build {@link AddEcoNewsDtoRequest} with unique title</li>
 *   <li>assert status + body</li>
 *   <li>clean up with {@code DELETE /eco-news/{id}}</li>
 * </ol>
 * Related US: Create news / post news (#86, #89).
 */
public class CreateEcoNewsApiTest extends AuthenticatedApiTestRunner {

    private static final String MIN_CONTENT =
            "Reference API content for create news, at least twenty chars.";

    private final List<Long> createdNewsIds = new ArrayList<>();

    @AfterEach
    void deleteCreatedNews() {
        for (Long id : createdNewsIds) {
            ecoNewsClient.delete(id);
        }
        createdNewsIds.clear();
    }

    @Test
    @DisplayName("POST /eco-news - author creates news successfully")
    void authorCanCreateEcoNews() {
        String title = uniqueTitle("API create");
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title(title)
                .text(MIN_CONTENT)
                .tags(List.of("news"))
                .source("")
                .shortInfo("API reference short info")
                .build();

        Response response = ecoNewsClient.create(request);
        assertEquals(201, response.statusCode(), response.asPrettyString());

        EcoNewsDto created = response.as(EcoNewsDto.class);
        assertNotNull(created.getId(), "Created news must have id");
        createdNewsIds.add(created.getId());

        EcoNewsDto fetched = ecoNewsClient.getByIdAsDto(created.getId());

        assertAll("Created news is readable by id",
                () -> assertEquals(title, created.getTitle()),
                () -> assertEquals(title, fetched.getTitle()),
                () -> assertTrue(
                        fetched.getContent() != null && fetched.getContent().contains("Reference API"),
                        "GET content should contain published text"));
    }

    @Test
    @DisplayName("POST /eco-news - without token returns 401")
    void createWithoutTokenReturns401() {
        EcoNewsClient guestClient = new EcoNewsClient(testValueProvider.getBaseAPIUrl());
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title(uniqueTitle("API unauthorized"))
                .text(MIN_CONTENT)
                .tags(List.of("news"))
                .build();

        Response response = guestClient.create(request);

        assertEquals(401, response.statusCode(), response.asPrettyString());
    }

    @Test
    @DisplayName("POST /eco-news - text shorter than 20 chars returns 400")
    void createWithTooShortTextReturns400() {
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title(uniqueTitle("API short text"))
                .text("too short")
                .tags(List.of("news"))
                .build();

        Response response = ecoNewsClient.create(request);

        assertEquals(400, response.statusCode(), response.asPrettyString());
    }

    private static String uniqueTitle(String prefix) {
        return prefix + " " + UUID.randomUUID();
    }
}
