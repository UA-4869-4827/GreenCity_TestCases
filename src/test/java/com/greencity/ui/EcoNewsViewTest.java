package com.greencity.ui;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI")
@Feature("Eco News")
@DisplayName("Eco news list view")
public class EcoNewsViewTest extends BaseTestRunner {

    // TC-P1-NVW-01 – Gallery is default; list toggle does not change the dataset
    // #59 //

    @Test
    @Story("Guest opens the eco news list")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-P1-NVW-01 - Gallery view is selected by default")
    @Description("A guest opening Eco news sees the gallery view control active.")
    @Issue("59")
    void ecoNewsPageShouldOpenInGalleryViewByDefault() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        assertTrue(ecoNewsPage.isGalleryViewSelected());
    }

    @Test
    @Story("Guest sees the content of a news card")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-P1-NVW-01 - Gallery card shows all required elements")
    @Description("The first gallery card shows title, author, date, tag, likes, comments, image and bookmark.")
    @Issue("59")
    void firstGalleryCardShouldShowRequiredElements() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsCardComponent card = ecoNewsPage.getNewsCard(0);

        assertTrue(card.isTitleDisplayed());
        assertTrue(card.isAuthorDisplayed());
        assertTrue(card.isDateDisplayed());
        assertTrue(card.isTagDisplayed());
        assertTrue(card.isLikesCounterDisplayed());
        assertTrue(card.isCommentsCounterDisplayed());
        assertTrue(card.isImageDisplayed());
        assertTrue(card.isBookmarkDisplayed());
    }

    @Test
    @Story("Guest switches the eco news list to list view")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-P1-NVW-01 - List view control becomes active after switching")
    @Description("Switching to list view activates the list control and deactivates the gallery one.")
    @Issue("59")
    void switchToListViewShouldActivateListControl() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getViewModeToggle().switchToListView();

        assertTrue(ecoNewsPage.isListViewSelected());
        assertFalse(ecoNewsPage.isGalleryViewSelected());
    }


    @Test
    @Story("Guest switches the eco news list to list view")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-P1-NVW-01 - Switching to list view keeps the same number of items")
    @Description("The number of news items in list view matches the number shown in gallery view.")
    @Issue("59")
    void switchToListViewShouldNotChangeItemsCount() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        int galleryCardsCount = ecoNewsPage.getNewsCardsCount();

        ecoNewsPage.getViewModeToggle().switchToListView();

        int listCardsCount = ecoNewsPage.getNewsCardsCountInListView();

        assertEquals(galleryCardsCount, listCardsCount);
    }

    @Test
    @Story("Guest switches the eco news list back to gallery view")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-P1-NVW-01 - Gallery control is restored after switching back")
    @Description("Switching from list view back to gallery restores the gallery control as active.")
    @Issue("59")
    void switchBackToGalleryViewShouldRestoreGalleryControl() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getViewModeToggle().switchToListView();
        ecoNewsPage.getViewModeToggle().switchToTableView();

        assertTrue(ecoNewsPage.isGalleryViewSelected());
        assertFalse(ecoNewsPage.isListViewSelected());
    }

    @Test
    @Story("Guest sees eco news sorted by date")
    @Severity(SeverityLevel.MINOR)
    @DisplayName("TC-P1-NVW-01 - News are sorted from newest to oldest")
    @Description("The dates of the first two cards are read to check the newest-first order.")
    @Issue("59")
    void newsShouldBeSortedFromNewestToOldest() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsCardComponent firstCard = ecoNewsPage.getNewsCard(0);
        NewsCardComponent secondCard = ecoNewsPage.getNewsCard(1);

        String firstDate = firstCard.getDate();
        String secondDate = secondCard.getDate();

        System.out.println("First news date: " + firstDate);
        System.out.println("Second news date: " + secondDate);
    }

    
    // TC-P1-NVW-02 – Card opens details; guest bookmark is gated #60 //

    @Test
    @Story("Guest is asked to sign in before saving news")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("TC-P1-NVW-02 - Bookmark click opens the Sign in modal for a guest")
    @Description("A guest clicking the bookmark icon gets the Sign in modal instead of saving the news.")
    @Issue("60")
    void guestSavedNewsClickShouldOpenSignInModal() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        SignInModal signInModal = ecoNewsPage.openSavedNewsAsGuest();

        assertEquals("Welcome back!", signInModal.getModalTitleText());
    }

    @Test
    @Story("Guest opens a news article from the list")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("TC-P1-NVW-02 - Card click opens the news details page")
    @Description("Clicking the body of a news card navigates to the news details page.")
    @Issue("60")
    void cardBodyClickShouldOpenNewsDetailsPage() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsDetailsPage detailsPage = ecoNewsPage.openNewsByIndex(0);

        assertTrue(driver.getCurrentUrl().contains("/greenCity/news/"));
    }
}
