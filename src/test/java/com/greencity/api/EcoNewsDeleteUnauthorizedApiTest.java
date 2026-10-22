package com.greencity.api;

import com.greencity.api.clients.EcoNewsClient;
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

public class EcoNewsDeleteUnauthorizedApiTest extends AuthenticatedApiTestRunner {

    private static final String NEWS_TEXT =
            "Autotest content for the unauthorized delete API test, longer than twenty characters.";

    private Long createdNewsId;

    @AfterEach
    void deleteNewsIfLeftOver() {
        if (createdNewsId != null) {
            ecoNewsClient.delete(createdNewsId);
            createdNewsId = null;
        }
    }

    @Test
    @DisplayName("DELETE /eco-news/{id} returns 401 without a token and keeps the news")
    void deleteNewsWithoutTokenReturnsUnauthorized() {
        EcoNewsDto created = createNews();
        createdNewsId = created.getId();

        EcoNewsClient guestClient = new EcoNewsClient(testValueProvider.getBaseAPIUrl());
        Response deleteResponse = guestClient.delete(createdNewsId);
        assertEquals(401, deleteResponse.statusCode(), deleteResponse.asPrettyString());

        Response getResponse = ecoNewsClient.getById(createdNewsId);
        assertEquals(200, getResponse.statusCode(),
                "News disappeared after an unauthorized delete");
    }

    private EcoNewsDto createNews() {
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title("Autotest unauthorized delete " + UUID.randomUUID())
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
