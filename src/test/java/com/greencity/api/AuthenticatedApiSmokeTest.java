package com.greencity.api;

import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Sample authenticated API smoke (sign-in via User service).
 */
public class AuthenticatedApiSmokeTest extends AuthenticatedApiTestRunner {

    @Test
    @DisplayName("Configured user can obtain accessToken via /ownSecurity/signIn")
    void configuredUserCanSignIn() {
        assertNotNull(accessToken);
        assertTrue(accessToken.length() > 20, "accessToken looks too short");
        assertNotNull(signedInUser.getUserId());
        assertNotNull(signedInUser.getName());
    }
}
