package com.greencity.ui;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EcoNewsFilterTest extends BaseTestRunner {

    // TC-P1-NFL-01 – Single and combined tag filters update count and tags #57
    @Test
    void educationFilterShouldUpdateCountAndShowEducationTag() {

        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        String initialCount = ecoNewsPage.getItemsFoundCount();

        ecoNewsPage.filterByEducation();

        String educationCount = ecoNewsPage.waitForItemsFoundCountToChange(initialCount);

        assertNotEquals(
                initialCount,
                educationCount,
                "Items count should change after selecting Education filter");

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        for (NewsCardComponent card : ecoNewsPage.getVisibleNewsCards()) {

            assertTrue(
                    card.getTags().stream()
                            .anyMatch(tag -> tag.equalsIgnoreCase(
                                    EcoNewsPage.NewsTag.EDUCATION.getText())),
                    "Card '" + card.getTitle()
                            + "' should contain Education tag. Actual tags: "
                            + card.getTags());
        }

        String educationCountBeforeEvents = ecoNewsPage.getItemsFoundCount();

        ecoNewsPage.filterByEvents();

        String combinedCount = ecoNewsPage.waitForItemsFoundCountToChange(
                educationCountBeforeEvents);

        assertNotEquals(
                educationCountBeforeEvents,
                combinedCount,
                "Items count should change after selecting Events together with Education");

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        for (NewsCardComponent card : ecoNewsPage.getVisibleNewsCards()) {

            boolean hasEducation = card.getTags().stream()
                    .anyMatch(tag -> tag.equalsIgnoreCase(
                            EcoNewsPage.NewsTag.EDUCATION.getText()));

            boolean hasEvents = card.getTags().stream()
                    .anyMatch(tag -> tag.equalsIgnoreCase(
                            EcoNewsPage.NewsTag.EVENTS.getText()));

            assertTrue(
                    hasEducation || hasEvents,
                    "Card '" + card.getTitle()
                            + "' should contain Education or Events tag. Actual tags: "
                            + card.getTags());
        }
    }

    // TC-P1-NFL-02 – Search finds a known title and clear restores the list #58
    @Test
    void searchKnownTitleShouldFilterResultsAndClearShouldRestoreList() {

        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        String initialCount = ecoNewsPage.getItemsFoundCount();

        String searchQuery = "News for comment testing";

        ecoNewsPage.openSearch();
        ecoNewsPage.searchNews(searchQuery);

        String searchCount = ecoNewsPage.waitForItemsFoundCountToChange(initialCount);

        assertNotEquals(
                initialCount,
                searchCount,
                "Items count should change after searching for a known title");

        ecoNewsPage.waitUntilSearchResultIsDisplayed(searchQuery);

        assertTrue(
                ecoNewsPage.getVisibleNewsCards().stream()
                        .anyMatch(card -> card.getTitle()
                                .trim()
                                .equalsIgnoreCase(searchQuery)),
                "Expected news '" + searchQuery
                        + "' should be displayed in search results");

        ecoNewsPage.clearSearch();

        String restoredCount = ecoNewsPage.waitForItemsFoundCountToChange(searchCount);

        assertTrue(
                restoredCount.equals(initialCount),
                "Clear search should restore the initial count. Expected: "
                        + initialCount
                        + ", but was: "
                        + restoredCount);

        assertTrue(
                !ecoNewsPage.getVisibleNewsCards().isEmpty(),
                "News cards should be displayed after clearing search");
    }
}