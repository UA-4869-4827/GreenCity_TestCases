package com.greencity.ui;

import com.greencity.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Sample guest UI test. Allure annotations on this class are the reference
 * for new tests. See docs/allure.md.
 */
@Epic("UI")
@Feature("Home")
@DisplayName("Home page")
public class BaseTest extends BaseTestRunner {

    @Test
    @Story("Guest sees the header logo")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Logo is displayed on the home page")
    @Description("Smoke check: a guest on the home page sees the header logo.")
    void logoShouldBeDisplayedOnHomePage() {
        assertTrue(homePage.getHeader().isLogoDisplayed(), "The header logo is not displayed.");
    }
}
