package com.greencity.api;

import com.greencity.api.clients.EcoNewsClient;
import com.greencity.api.models.econews.AddEcoNewsDtoRequest;
import com.greencity.api.models.econews.EcoNewsDto;
import com.greencity.api.support.ApiAuth;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeleteEcoNewsOtherUserApiTest extends AuthenticatedApiTestRunner {

    private static final String MIN_CONTENT = "API test content for another user's news deletion check.";

    private final List<Long> createdNewsIds = new ArrayList<>();

    @AfterEach
    void deleteCreatedNews() {
        for (Long id : createdNewsIds) {
            ecoNewsClient.delete(id);
        }
        createdNewsIds.clear();
    }

    @Test
    @DisplayName("Other user cannot delete another user's news")
    void otherUserCannotDeleteNews() {
        assertFalse(testValueProvider.getSecondUserEmail().isBlank(),
                "Configure second.user.email in config.properties");
        assertFalse(testValueProvider.getSecondUserPassword().isBlank(),
                "Configure second.user.password in config.properties");

        String title = "API delete authorization " + UUID.randomUUID();
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title(title)
                .text(MIN_CONTENT)
                .tags(List.of("news"))
                .source("")
                .shortInfo("API test short info")
                .build();

        Response createResponse = ecoNewsClient.create(request);
        assertEquals(201, createResponse.statusCode(), createResponse.asPrettyString());

        EcoNewsDto createdNews = createResponse.as(EcoNewsDto.class);
        Long newsId = createdNews.getId();
        assertNotNull(newsId, "Created news must have an id");
        createdNewsIds.add(newsId);

        String secondUserToken = ApiAuth.signInAsSecondUser(testValueProvider).getAccessToken();
        EcoNewsClient secondUserClient = new EcoNewsClient(testValueProvider.getBaseAPIUrl(), secondUserToken);

        Response deleteResponse = secondUserClient.delete(newsId);
        assertTrue(
                deleteResponse.statusCode() == 403
                        || (deleteResponse.statusCode() == 400
                                && deleteResponse.asString().contains("Current user has no permission")),
                "Expected permission denial, but got "
                        + deleteResponse.statusCode() + ": " + deleteResponse.asString());

        Response getResponse = ecoNewsClient.getById(newsId);
        assertEquals(200, getResponse.statusCode(), getResponse.asPrettyString());
    }
}
