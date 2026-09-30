package com.greencity.api.testRunners;

import com.greencity.api.clients.EcoNewsClient;
import com.greencity.api.models.auth.SignInResponse;
import com.greencity.api.support.ApiAuth;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Assumptions;

/**
 * Signs in once via User service ({@code base.user.api.url}) and exposes
 * {@link #accessToken} plus an authorized {@link #ecoNewsClient}.
 * <p>
 * Skipped when {@code user.email} / {@code user.password} are missing or still placeholders.
 */
@Tag("api")
@Tag("auth")
public abstract class AuthenticatedApiTestRunner extends ApiTestRunner {

    protected static String accessToken;
    protected static SignInResponse signedInUser;

    @BeforeAll
    static void signInForApi() {
        Assumptions.assumeTrue(
                testValueProvider.hasConfiguredUserCredentials(),
                "Set real user.email / user.password in config.properties "
                        + "(or USER_EMAIL / USER_PASSWORD) to run @Tag(\"auth\") API tests");

        signedInUser = ApiAuth.signInAsConfiguredUser(testValueProvider);
        accessToken = signedInUser.getAccessToken();
        Assumptions.assumeTrue(
                accessToken != null && !accessToken.isBlank(),
                "Sign-in did not return accessToken");

        ecoNewsClient = new EcoNewsClient(testValueProvider.getBaseAPIUrl(), accessToken);
    }
}
