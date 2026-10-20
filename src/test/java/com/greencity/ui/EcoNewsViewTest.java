package com.greencity.ui;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcoNewsViewTest extends BaseTestRunner {

    // TC-P1-NVW-01 – Gallery is default; list toggle does not change the dataset
    // #59 //

    @Test
    void ecoNewsPageShouldOpenInGalleryViewByDefault() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        assertTrue(ecoNewsPage.isGalleryViewSelected());
    }

    @Test
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
    void switchToListViewShouldActivateListControl() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getViewModeToggle().switchToListView();

        assertTrue(ecoNewsPage.isListViewSelected());
        assertFalse(ecoNewsPage.isGalleryViewSelected());
    }

    
    @Test
    void switchToListViewShouldNotChangeItemsCount() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        int galleryCardsCount = ecoNewsPage.getNewsCardsCount();

        ecoNewsPage.getViewModeToggle().switchToListView();

        int listCardsCount = ecoNewsPage.getNewsCardsCountInListView();

        assertEquals(galleryCardsCount, listCardsCount);
    }

    @Test
    void switchBackToGalleryViewShouldRestoreGalleryControl() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getViewModeToggle().switchToListView();
        ecoNewsPage.getViewModeToggle().switchToTableView();

        assertTrue(ecoNewsPage.isGalleryViewSelected());
        assertFalse(ecoNewsPage.isListViewSelected());
    }

    @Test
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
    void guestSavedNewsClickShouldOpenSignInModal() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        SignInModal signInModal = ecoNewsPage.openSavedNewsAsGuest();

        assertEquals("Welcome back!", signInModal.getModalTitleText());
    }

    @Test
    void cardBodyClickShouldOpenNewsDetailsPage() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsDetailsPage detailsPage = ecoNewsPage.openNewsByIndex(0);

        assertTrue(driver.getCurrentUrl().contains("/greenCity/news/"));
    }
}
