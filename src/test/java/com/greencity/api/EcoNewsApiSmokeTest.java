package com.greencity.api;

import com.greencity.api.testRunners.ApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Sample guest API smoke. Extend this pattern for API TCs (news, events, comments).
 */
public class EcoNewsApiSmokeTest extends ApiTestRunner {

    @Test
    @DisplayName("GET /eco-news returns a page of news")
    void getEcoNewsListReturnsOk() {
        Response response = ecoNewsClient.getAll(0, 5);

        assertEquals(200, response.statusCode());

        List<?> items = response.jsonPath().getList("page");
        if (items == null) {
            items = response.jsonPath().getList("content");
        }
        assertNotNull(items, "Expected page or content array in response");
        assertFalse(items.isEmpty(), "Expected at least one eco-news item");
    }
}
