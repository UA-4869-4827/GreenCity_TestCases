package com.greencity.ui;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EcoNewsFilterTest extends BaseTestRunner {

    private static final Logger LOG = LoggerFactory.getLogger(EcoNewsFilterTest.class);

    @Test
    void educationFilterShouldUpdateCountAndShowEducationTag() {
        LOG.info("START TEST: Education filter updates count and shows Education tag");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        String initialCount = ecoNewsPage.getItemsFoundCount();
        LOG.info("Initial items count: '{}'", initialCount);

        LOG.info("Applying 'Education' filter");
        ecoNewsPage.filterByEducation();

        String educationCount = ecoNewsPage.waitForItemsFoundCountToChange(initialCount);
        LOG.info("Items count after Education filter: '{}'", educationCount);

        assertNotEquals(
                initialCount,
                educationCount,
                "Items count should change after selecting Education filter");

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        List<NewsCardComponent> educationCards = ecoNewsPage.getVisibleNewsCards();
        LOG.info("Education filter: {} cards displayed", educationCards.size());

        assertTrue(
                !educationCards.isEmpty(),
                "News cards should be displayed after applying Education filter");

        for (NewsCardComponent card : educationCards) {
            LOG.debug("Checking card '{}', tags={}", card.getTitle(), card.getTags());

            assertTrue(
                    card.getTags().stream()
                            .anyMatch(tag -> tag.equalsIgnoreCase(
                                    EcoNewsPage.NewsTag.EDUCATION.getText())),
                    "Card '" + card.getTitle()
                            + "' should contain Education tag. Actual tags: "
                            + card.getTags());
        }

        String educationCountBeforeEvents = ecoNewsPage.getItemsFoundCount();

        LOG.info("Adding 'Events' filter together with Education");
        ecoNewsPage.filterByEvents();

        String combinedCount = ecoNewsPage.waitForItemsFoundCountToChange(
                educationCountBeforeEvents);
        LOG.info("Items count after Education + Events: '{}' (was '{}')",
                combinedCount, educationCountBeforeEvents);

        assertNotEquals(
                educationCountBeforeEvents,
                combinedCount,
                "Items count should change after selecting Events together with Education");

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        List<NewsCardComponent> combinedCards = ecoNewsPage.getVisibleNewsCards();
        LOG.info("Combined filters: {} cards displayed", combinedCards.size());

        assertTrue(
                !combinedCards.isEmpty(),
                "News cards should be displayed after applying combined filters");

        for (NewsCardComponent card : combinedCards) {
            LOG.debug("Checking card '{}', tags={}", card.getTitle(), card.getTags());

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

        LOG.info("END TEST: passed");
    }

    // TC-P1-NFL-02 – Search finds a known title and clear restores the list #58
    @Test
    void searchKnownTitleShouldFilterResultsAndClearShouldRestoreList() {
        LOG.info("START TEST: Search known title and clear restores list (#58)");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.waitUntilNewsCardsAreDisplayed();

        String initialCount = ecoNewsPage.getItemsFoundCount();
        LOG.info("Initial items count: '{}'", initialCount);

        String searchQuery = "News for comment testing";

        LOG.info("Searching for '{}'", searchQuery);
        ecoNewsPage.openSearch();
        ecoNewsPage.searchNews(searchQuery);

        String searchCount = ecoNewsPage.waitForItemsFoundCountToChange(initialCount);
        LOG.info("Items count after search: '{}'", searchCount);

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

        LOG.info("Clearing search");
        ecoNewsPage.clearSearch();

        String restoredCount = ecoNewsPage.waitForItemsFoundCountToChange(searchCount);
        LOG.info("Items count after clearing search: '{}'", restoredCount);

        assertEquals(
                initialCount,
                restoredCount,
                "Clear search should restore the initial count");

        assertTrue(
                !ecoNewsPage.getVisibleNewsCards().isEmpty(),
                "News cards should be displayed after clearing search");

        LOG.info("END TEST: passed");
    }
}