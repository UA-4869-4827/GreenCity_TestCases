package com.greencity.api.support;

import com.greencity.api.clients.AuthClient;
import com.greencity.api.models.auth.SignInResponse;
import com.greencity.utils.TestValueProvider;

/**
 * Helper to obtain a JWT from the User service for API tests.
 */
public final class ApiAuth {

    private ApiAuth() {
    }

    public static SignInResponse signInAsConfiguredUser(TestValueProvider values) {
        AuthClient authClient = new AuthClient(values.getBaseUserApiUrl());
        return authClient.signIn(values.getUserEmail(), values.getUserPassword());
    }

    public static String accessTokenForConfiguredUser(TestValueProvider values) {
        return signInAsConfiguredUser(values).getAccessToken();
    }
}
