package com.greencity.ui.news;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcoNewsGuestNavigationTest extends BaseTestRunner {

    @Test
    @Tag("smoke")
    void headerAndReadAllNewsOpenEcoNewsList() {
        EcoNewsPage ecoNewsFromHeader = homePage.getHeader().openEcoNews();
        assertEcoNewsListPage(ecoNewsFromHeader);

        HomePage home = ecoNewsFromHeader.getHeader().clickLogo();
        assertTrue(home.isOpened(), "Returning via the header logo should open Home (#/greenCity).");

        EcoNewsPage ecoNewsFromHome = home.openAllNews();
        assertEcoNewsListPage(ecoNewsFromHome);
    }

    private void assertEcoNewsListPage(EcoNewsPage ecoNewsPage) {
        assertTrue(ecoNewsPage.isOpened(), "The Eco news list should open at #/greenCity/news.");
        assertEquals(UiMessage.NEWS_PAGE_HEADING.text(), ecoNewsPage.getHeadingText(),
                "The page H1 should be Eco news.");
        assertTrue(ecoNewsPage.getHeader().isEcoNewsNavActive(),
                "The Eco news header item should be active.");
        assertTrue(ecoNewsPage.areFilterChipsDisplayed(), "Filter chips should be visible.");
        assertTrue(ecoNewsPage.isItemsFoundCounterDisplayed(),
                "The items-found counter should be visible.");
        assertTrue(ecoNewsPage.getItemsFoundText().contains(UiMessage.NEWS_ITEMS_FOUND.text()),
                "The items-found counter should include the found-items label.");
        assertTrue(ecoNewsPage.getViewModeToggle().isDisplayed(),
                "The gallery/list toggle should be visible.");
        assertTrue(ecoNewsPage.getViewModeToggle().isTableViewPressed(),
                "Gallery should be the default view.");
        assertFalse(ecoNewsPage.getViewModeToggle().isListViewPressed(),
                "List view should not be selected by default.");
        assertTrue(ecoNewsPage.isAtLeastOneNewsCardDisplayed(),
                "At least one news card should be visible.");
        assertTrue(ecoNewsPage.getHeader().isLogoDisplayed(),
                "The shared GreenCity header should be visible.");
        assertTrue(ecoNewsPage.getFooter().isDisplayed(),
                "The shared GreenCity footer should be visible.");
    }
}
