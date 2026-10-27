package com.greencity.ui;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Eco News")
@Feature("News filter and search")
class EcoNewsFilterTest extends BaseTestRunner {

    // TC-P1-NFL-01 – Single and combined tag filters update count and tags #57
    @Test
    @DisplayName("TC-P1-NFL-01: Single and combined tag filters update count and tags")
    @Story("Filter by tags")
    @Severity(SeverityLevel.NORMAL)
    @Description("Education filter and combined Education + Events filter "
            + "change the items count and show cards with matching tags.")
    void educationFilterShouldUpdateCountAndShowEducationTag() {

        EcoNewsPage ecoNewsPage = Allure.step("Open Eco News page", () -> {
            EcoNewsPage page = homePage.getHeader().openEcoNews();
            page.waitUntilNewsCardsAreDisplayed();
            return page;
        });

        String initialCount = Allure.step("Read initial items count", () ->
                ecoNewsPage.getItemsFoundCount());

        Allure.step("Apply Education filter and verify items count changed", () -> {
            ecoNewsPage.filterByEducation();

            String educationCount = ecoNewsPage.waitForItemsFoundCountToChange(initialCount);

            assertNotEquals(
                    initialCount,
                    educationCount,
                    "Items count should change after selecting Education filter");
        });

        List<NewsCardComponent> educationCards = Allure.step("Get visible cards after Education filter", () -> {
            ecoNewsPage.waitUntilNewsCardsAreDisplayed();
            return ecoNewsPage.getVisibleNewsCards();
        });

        Allure.step("Verify every card has Education tag", () -> {
            assertTrue(
                    !educationCards.isEmpty(),
                    "News cards should be displayed after applying Education filter");

            for (NewsCardComponent card : educationCards) {

                assertTrue(
                        card.getTags().stream()
                                .anyMatch(tag -> tag.equalsIgnoreCase(
                                        EcoNewsPage.NewsTag.EDUCATION.getText())),
                        "Card '" + card.getTitle()
                                + "' should contain Education tag. Actual tags: "
                                + card.getTags());
            }
        });

        String educationCountBeforeEvents = Allure.step("Read items count before adding Events filter", () ->
                ecoNewsPage.getItemsFoundCount());

        Allure.step("Add Events filter and verify items count changed", () -> {
            ecoNewsPage.filterByEvents();

            String combinedCount = ecoNewsPage.waitForItemsFoundCountToChange(
                    educationCountBeforeEvents);

            assertNotEquals(
                    educationCountBeforeEvents,
                    combinedCount,
                    "Items count should change after selecting Events together with Education");
        });

        List<NewsCardComponent> combinedCards = Allure.step("Get visible cards after combined filters", () -> {
            ecoNewsPage.waitUntilNewsCardsAreDisplayed();
            return ecoNewsPage.getVisibleNewsCards();
        });

        Allure.step("Verify every card has Education or Events tag", () -> {
            assertTrue(
                    !combinedCards.isEmpty(),
                    "News cards should be displayed after applying combined filters");

            for (NewsCardComponent card : combinedCards) {

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
        });
    }

    // TC-P1-NFL-02 – Search finds a known title and clear restores the list #58
    @Test
    @DisplayName("TC-P1-NFL-02: Search finds a known title and clear restores the list")
    @Story("Search news")
    @Severity(SeverityLevel.NORMAL)
    @Description("Searching a known title filters the list; clearing the search restores it.")
    void searchKnownTitleShouldFilterResultsAndClearShouldRestoreList() {

        EcoNewsPage ecoNewsPage = Allure.step("Open Eco News page", () -> {
            EcoNewsPage page = homePage.getHeader().openEcoNews();
            page.waitUntilNewsCardsAreDisplayed();
            return page;
        });

        String initialCount = Allure.step("Read initial items count", () ->
                ecoNewsPage.getItemsFoundCount());

        String searchQuery = "News for comment testing";

        String searchCount = Allure.step("Search news: " + searchQuery, () -> {
            ecoNewsPage.openSearch();
            ecoNewsPage.searchNews(searchQuery);

            String count = ecoNewsPage.waitForItemsFoundCountToChange(initialCount);

            assertNotEquals(
                    initialCount,
                    count,
                    "Items count should change after searching for a known title");
            return count;
        });

        Allure.step("Verify the searched news is displayed in results", () -> {
            ecoNewsPage.waitUntilSearchResultIsDisplayed(searchQuery);

            assertTrue(
                    ecoNewsPage.getVisibleNewsCards().stream()
                            .anyMatch(card -> card.getTitle()
                                    .trim()
                                    .equalsIgnoreCase(searchQuery)),
                    "Expected news '" + searchQuery
                            + "' should be displayed in search results");
        });

        Allure.step("Clear search and verify the list is restored", () -> {
            ecoNewsPage.clearSearch();

            String restoredCount = ecoNewsPage.waitForItemsFoundCountToChange(searchCount);

            assertEquals(
                    initialCount,
                    restoredCount,
                    "Clear search should restore the initial count");

            assertTrue(
                    !ecoNewsPage.getVisibleNewsCards().isEmpty(),
                    "News cards should be displayed after clearing search");
        });
    }
}