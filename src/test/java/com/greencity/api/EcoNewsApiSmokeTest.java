package com.greencity.api;

import com.greencity.api.testRunners.ApiTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Sample guest API smoke. Allure annotations here follow docs/allure.md.
 */
@Epic("API")
@Feature("Eco News")
@DisplayName("Eco news API")
public class EcoNewsApiSmokeTest extends ApiTestRunner {

    @Test
    @Story("Guest lists eco news")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("GET /eco-news returns a page of news")
    @Description("Public GET /eco-news returns HTTP 200 and a non-empty page.")
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
