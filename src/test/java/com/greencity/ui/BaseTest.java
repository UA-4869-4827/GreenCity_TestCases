package com.greencity.ui;

import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest extends BaseTestRunner {

    @Test
    void logoShouldBeDisplayedOnHomePage() {
        assertTrue(homePage.getHeader().isLogoDisplayed(), "The header logo is not displayed.");
    }
}
