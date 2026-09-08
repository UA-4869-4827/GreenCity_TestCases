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

/**
 * TC-P1-NVW-01 – Gallery is default; list toggle does not change the dataset
 * TC-P1-NVW-02 – Card opens details; guest bookmark is gated
 * Parent US: #24
 */
public class EcoNewsViewTest extends BaseTestRunner {

    // TC-P1-NVW-01 – Gallery is default; list toggle does not change the dataset #59 //

    @Test
    void ecoNewsPageShouldOpenInGalleryViewByDefault() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        assertTrue(ecoNewsPage.isGalleryViewSelected(),
                "Gallery (table) view should be active by default");
    }

    @Test
    void firstGalleryCardShouldShowRequiredElements() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsCardComponent card = ecoNewsPage.getNewsCard(0);

        assertTrue(card.isTitleDisplayed(), "Card title should be displayed");
        assertTrue(card.isAuthorDisplayed(), "Card author should be displayed");
        assertTrue(card.isDateDisplayed(), "Card date should be displayed");
        assertTrue(card.isTagDisplayed(), "Card tag should be displayed");
        assertTrue(card.isLikesCounterDisplayed(), "Card likes count should be displayed");
        assertTrue(card.isCommentsCounterDisplayed(), "Card comments count should be displayed");
    }

    @Test
    void switchToListViewShouldActivateListControl() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getViewModeToggle().switchToListView();

        assertTrue(ecoNewsPage.isListViewSelected(),
                "List view control should be pressed after switching");
        assertFalse(ecoNewsPage.isGalleryViewSelected(),
                "Gallery view control should not be pressed after switching to list");
    }

    @Test
    void switchToListViewShouldNotChangeItemsCount() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        int galleryCardsCount = ecoNewsPage.getNewsCardsCount();

        ecoNewsPage.getViewModeToggle().switchToListView();

        int listCardsCount = ecoNewsPage.getNewsCardsCountInListView();

        assertEquals(galleryCardsCount, listCardsCount,
                "Items count should not change after switching to list view");
    }

    @Test
    void switchBackToGalleryViewShouldRestoreGalleryControl() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        ecoNewsPage.getViewModeToggle().switchToListView();
        ecoNewsPage.getViewModeToggle().switchToTableView();

        assertTrue(ecoNewsPage.isGalleryViewSelected(),
                "Gallery view should be restored after switching back");
        assertFalse(ecoNewsPage.isListViewSelected(),
                "List view control should not be pressed after restoring gallery");
    }

    // TC-P1-NVW-02 – Card opens details; guest bookmark is gated #60 //

    /**
     * Guest bookmark gate: clicking the "Saved news" button (bookmark-img)
     * on the EcoNews page header requires sign-in.
     * Note: per-card bookmark icon is CSS :hover-only and not accessible via WebDriver.
     */
    @Test
    void guestSavedNewsClickShouldOpenSignInModal() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        SignInModal signInModal = ecoNewsPage.openSavedNewsAsGuest();

        assertEquals("Welcome back!", signInModal.getModalTitleText(),
                "Sign in modal should open when guest clicks saved news bookmark");
    }

    @Test
    void cardBodyClickShouldOpenNewsDetailsPage() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsDetailsPage detailsPage = ecoNewsPage.openNewsByIndex(0);

        assertTrue(driver.getCurrentUrl().contains("/greenCity/news/"),
                "URL should contain /greenCity/news/ after clicking a card");
    }
}
