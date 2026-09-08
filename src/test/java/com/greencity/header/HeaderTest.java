package com.greencity.header;

import com.greencity.ui.component.header.HeaderComponent;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeaderTest extends BaseTestRunner {
    @Test
    @DisplayName("TC-P0-NAV-01 – Header opens GreenCity sections")
    void testHeaderNavigation() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        Assertions.assertTrue(
                ecoNewsPage.isPageHeadingDisplayed(),
                "Eco News page heading is not displayed"
        );

        EventsPage eventsPage = ecoNewsPage.getHeader().openEvents();

        Assertions.assertTrue(
                eventsPage.isPageHeadingDisplayed(),
                "Events page heading is not displayed"
        );
    }
}
