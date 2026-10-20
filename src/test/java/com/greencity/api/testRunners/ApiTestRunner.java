package com.greencity.api.testRunners;

import com.greencity.api.clients.EcoNewsClient;
import com.greencity.api.clients.EventsClient;
import com.greencity.utils.TestValueProvider;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;

/**
 * Base for guest / public API tests against {@code base.api.url}.
 * For JWT-backed calls use {@link AuthenticatedApiTestRunner}.
 */
@Tag("api")
public abstract class ApiTestRunner {

    protected static TestValueProvider testValueProvider;
    protected static EcoNewsClient ecoNewsClient;
    protected static EventsClient eventsClient;

    @BeforeAll
    static void setUpApi() {
        testValueProvider = new TestValueProvider();
        RestAssured.registerParser("application/problem+json", Parser.JSON);
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        ecoNewsClient = new EcoNewsClient(testValueProvider.getBaseAPIUrl());
        eventsClient = new EventsClient(testValueProvider.getBaseAPIUrl());
    }
}
