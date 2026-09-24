package com.greencity.ui;

import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Sample logged-in session check. Extend {@link AuthenticatedBaseTestRunner} the same way
 * for Create News / My Space / etc.
 */
public class AuthenticatedSessionTest extends AuthenticatedBaseTestRunner {

    @Test
    @DisplayName("Configured user lands on profile and appears logged in")
    void configuredUserIsLoggedInOnProfile() {
        assertTrue(profilePage.isOpened(), "Expected GreenCity profile after sign in");
        assertTrue(profilePage.getHeader().isLoggedIn(), "User menu should be visible after sign in");
    }
}
