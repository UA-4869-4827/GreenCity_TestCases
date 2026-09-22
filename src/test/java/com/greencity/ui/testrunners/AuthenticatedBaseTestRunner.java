package com.greencity.ui.testrunners;

import com.greencity.ui.page.profile.ProfilePage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;

/**
 * Starts each test as a guest on Home, then signs in through the UI Sign in modal
 * using {@code user.email} / {@code user.password} (or {@code USER_EMAIL} / {@code USER_PASSWORD}).
 * <p>
 * Use for TCs that need an authenticated session. Guest TCs stay on {@link BaseTestRunner}.
 */
@Tag("auth")
public abstract class AuthenticatedBaseTestRunner extends BaseTestRunner {

    protected ProfilePage profilePage;

    @BeforeEach
    void signInAsConfiguredUser() {
        Assumptions.assumeTrue(
                hasConfiguredUserCredentials(),
                "Set real user.email / user.password in config.properties "
                        + "(or USER_EMAIL / USER_PASSWORD env) to run @Tag(\"auth\") tests");
        profilePage = loginAsUser(ProfilePage.class);
    }
}
