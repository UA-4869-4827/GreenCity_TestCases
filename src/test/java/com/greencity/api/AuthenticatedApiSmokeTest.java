package com.greencity.api;

import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Sample authenticated API smoke (sign-in via User service).
 */
@Epic("API")
@Feature("Authentication")
@DisplayName("Authenticated API smoke tests")
public class AuthenticatedApiSmokeTest extends AuthenticatedApiTestRunner {

    @Test
    @Story("Configured user authenticates successfully")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Configured user can obtain accessToken via /ownSecurity/signIn")
    @Description("""
            Verify that the configured test user can authenticate through
            /ownSecurity/signIn and receive a valid access token.
            Expected result: the access token is present and sufficiently long,
            and the signed-in user has an ID and a name.
            """)
    void configuredUserCanSignIn() {
        assertNotNull(accessToken);
        assertTrue(accessToken.length() > 20, "accessToken looks too short");
        assertNotNull(signedInUser.getUserId());
        assertNotNull(signedInUser.getName());
    }
}
