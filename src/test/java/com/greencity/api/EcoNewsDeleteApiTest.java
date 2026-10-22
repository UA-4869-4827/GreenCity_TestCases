package com.greencity.api;

import com.greencity.api.models.econews.AddEcoNewsDtoRequest;
import com.greencity.api.models.econews.EcoNewsDto;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcoNewsDeleteApiTest extends AuthenticatedApiTestRunner {

    private static final long NON_EXISTING_NEWS_ID = Long.MAX_VALUE;
    private static final String NEWS_TEXT =
            "Autotest content for the delete news API test, longer than twenty characters.";

    private Long createdNewsId;

    @AfterEach
    void deleteNewsIfLeftOver() {
        if (createdNewsId != null) {
            ecoNewsClient.delete(createdNewsId);
            createdNewsId = null;
        }
    }

    @Test
    @DisplayName("DELETE /eco-news/{id} returns 200 and the news is gone")
    void deleteOwnNewsMakesItUnavailable() {
        EcoNewsDto created = createNews("Autotest delete");
        createdNewsId = created.getId();

        Response deleteResponse = ecoNewsClient.delete(createdNewsId);
        assertEquals(200, deleteResponse.statusCode(), deleteResponse.asPrettyString());
        assertTrue(deleteResponse.asString().isBlank(), "Delete response body should be empty");

        Response getResponse = ecoNewsClient.getById(createdNewsId);
        assertEquals(404, getResponse.statusCode(),
                "Deleted news is still available by id " + createdNewsId);
        createdNewsId = null;
    }

    @Test
    @DisplayName("DELETE /eco-news/{id} returns 404 for a non-existent news")
    void deleteNonExistingNewsReturnsNotFound() {
        Response response = ecoNewsClient.delete(NON_EXISTING_NEWS_ID);

        assertEquals(404, response.statusCode(),
                "Unexpected status for DELETE /eco-news/" + NON_EXISTING_NEWS_ID);
    }

    private EcoNewsDto createNews(String titlePrefix) {
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title(titlePrefix + " " + UUID.randomUUID())
                .text(NEWS_TEXT)
                .tags(List.of("news"))
                .source("")
                .shortInfo("Autotest short info")
                .build();

        Response response = ecoNewsClient.create(request);
        assertEquals(201, response.statusCode(), response.asPrettyString());
        EcoNewsDto created = response.as(EcoNewsDto.class);
        assertNotNull(created.getId(), "Created news has no id");
        return created;
    }
}
