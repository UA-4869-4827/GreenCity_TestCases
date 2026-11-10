package com.greencity.ui;

import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EcoNewsViewTest extends BaseTestRunner {

    private static final Logger LOG = LoggerFactory.getLogger(EcoNewsViewTest.class);

    @Test
    void ecoNewsPageShouldOpenInGalleryViewByDefault() {
        LOG.info("START TEST: Eco News opens in gallery view by default");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        assertTrue(ecoNewsPage.isGalleryViewSelected(),
                "Gallery view should be selected by default");

        LOG.info("END TEST: passed");
    }

    @Test
    void firstGalleryCardShouldShowRequiredElements() {
        LOG.info("START TEST: First gallery card shows required elements");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsCardComponent card = ecoNewsPage.getNewsCard(0);
        LOG.info("Checking elements of the first card: '{}'", card.getTitle());


        assertTrue(card.isTitleDisplayed(), "Title should be displayed");
        assertTrue(card.isAuthorDisplayed(), "Author should be displayed");
        assertTrue(card.isDateDisplayed(), "Date should be displayed");
        assertTrue(card.isTagDisplayed(), "Tag should be displayed");
        assertTrue(card.isLikesCounterDisplayed(), "Likes counter should be displayed");
        assertTrue(card.isCommentsCounterDisplayed(), "Comments counter should be displayed");
        assertTrue(card.isImageDisplayed(), "Image should be displayed");
        assertTrue(card.isBookmarkDisplayed(), "Bookmark should be displayed");

        LOG.info("END TEST: passed");
    }

    @Test
    void switchToListViewShouldActivateListControl() {
        LOG.info("START TEST: Switch to list view activates list control");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        LOG.info("Switching to list view");
        ecoNewsPage.getViewModeToggle().switchToListView();

        assertTrue(ecoNewsPage.isListViewSelected(),
                "List view should be selected");
        assertFalse(ecoNewsPage.isGalleryViewSelected(),
                "Gallery view should not be selected");

        LOG.info("END TEST: passed");
    }

    @Test
    void switchToListViewShouldNotChangeItemsCount() {
        LOG.info("START TEST: Switch to list view does not change items count");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        int galleryCardsCount = ecoNewsPage.getNewsCardsCount();
        LOG.info("Cards count in gallery view: {}", galleryCardsCount);

        LOG.info("Switching to list view");
        ecoNewsPage.getViewModeToggle().switchToListView();

        int listCardsCount = ecoNewsPage.getNewsCardsCountInListView();
        LOG.info("Cards count in list view: {}", listCardsCount);

        assertEquals(galleryCardsCount, listCardsCount,
                "Items count should be the same in gallery and list views");

        LOG.info("END TEST: passed");
    }

    @Test
    void switchBackToGalleryViewShouldRestoreGalleryControl() {
        LOG.info("START TEST: Switch back to gallery view restores gallery control");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        LOG.info("Switching to list view");
        ecoNewsPage.getViewModeToggle().switchToListView();

        LOG.info("Switching back to gallery view");
        ecoNewsPage.getViewModeToggle().switchToTableView();

        assertTrue(ecoNewsPage.isGalleryViewSelected(),
                "Gallery view should be selected again");
        assertFalse(ecoNewsPage.isListViewSelected(),
                "List view should not be selected");

        LOG.info("END TEST: passed");
    }

    @Test
    void newsShouldBeSortedFromNewestToOldest() {
        LOG.info("START TEST: News sorted from newest to oldest");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        NewsCardComponent firstCard = ecoNewsPage.getNewsCard(0);
        NewsCardComponent secondCard = ecoNewsPage.getNewsCard(1);

        String firstDate = firstCard.getDate();
        String secondDate = secondCard.getDate();

        LOG.info("First news date: {}", firstDate);
        LOG.info("Second news date: {}", secondDate);

        LOG.info("END TEST: finished (no assertion yet)");
    }


    @Test
    void guestSavedNewsClickShouldOpenSignInModal() {
        LOG.info("START TEST: Guest clicks saved news and sees Sign In modal (#60)");

        LOG.info("Opening Eco News page as guest");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        LOG.info("Clicking saved news as guest");
        SignInModal signInModal = ecoNewsPage.openSavedNewsAsGuest();

        String actualTitle = signInModal.getModalTitleText();
        LOG.debug("Modal title actual='{}'", actualTitle);
        assertEquals("Welcome back!", actualTitle,
                "Sign In modal title is incorrect");

        LOG.info("END TEST: passed");
    }

    @Test
    void cardBodyClickShouldOpenNewsDetailsPage() {
        LOG.info("START TEST: Card body click opens news details page (#60)");

        LOG.info("Opening Eco News page");
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        LOG.info("Opening news by index 0");
        NewsDetailsPage detailsPage = ecoNewsPage.openNewsByIndex(0);

        String currentUrl = driver.getCurrentUrl();
        LOG.info("Current URL: {}", currentUrl);
        assertTrue(currentUrl.contains("/greenCity/news/"),
                "URL should contain '/greenCity/news/' but was: " + currentUrl);

        LOG.info("END TEST: passed");
    }
}