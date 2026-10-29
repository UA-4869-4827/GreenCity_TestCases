package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteNewsTest extends AuthenticatedBaseTestRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DeleteNewsTest.class);
    private static final String NOT_FOUND_MESSAGE = "The Eco-news was not found";

    private long createdNewsId;

    @Test
    void userShouldBeAbleToDeleteOwnNews() {
        String title = "Delete News Test " + System.nanoTime();
        LOG.info("Deleting own news");
        NewsDetailsPage newsDetailsPage = publishNews(title, "Test content for news deletion");
        long newsId = newsDetailsPage.getNewsId();
        createdNewsId = newsId;

        newsDetailsPage.openDeleteConfirmation();
        assertTrue(newsDetailsPage.isDeleteConfirmationDisplayed(),
                "The deletion confirmation dialog is not displayed");

        EcoNewsPage ecoNewsAfterDelete = newsDetailsPage.confirmDelete();
        createdNewsId = 0;

        assertTrue(ecoNewsAfterDelete.isNewsListOpened(),
                "The user was not redirected to the Eco news list");
        assertFalse(ecoNewsAfterDelete.isNewsDisplayedByTitle(title),
                "The deleted news is still displayed in the list");

        ecoNewsAfterDelete.openDeletedNewsUrl(newsId);

        assertTrue(ecoNewsAfterDelete.isNewsListOpened(),
                "The deleted news URL did not stay on the Eco news list");
        assertEquals(NOT_FOUND_MESSAGE, ecoNewsAfterDelete.getNewsNotFoundMessageText(),
                "The missing-news message was not displayed");
    }

    @Test
    void userShouldBeAbleToCancelNewsDeletion() {
        String title = "Cancel Delete News Test " + System.nanoTime();
        LOG.info("Cancelling deletion of own news");
        NewsDetailsPage newsDetailsPage = publishNews(title, "Test content for cancelling news deletion");
        createdNewsId = newsDetailsPage.getNewsId();

        newsDetailsPage.openDeleteConfirmation();
        assertTrue(newsDetailsPage.isDeleteConfirmationDisplayed(),
                "The deletion confirmation dialog is not displayed");

        newsDetailsPage.cancelDelete();

        assertEquals(title, newsDetailsPage.getTitleText(),
                "The user did not remain on the news details page");
        newsDetailsPage.open(createdNewsId);
        assertEquals(title, newsDetailsPage.getTitleText(),
                "The news is not available after refresh");
    }

    @AfterEach
    void deleteCreatedNews() {
        if (createdNewsId > 0) {
            new NewsDetailsPage(driver).open(createdNewsId).deleteNews();
            createdNewsId = 0;
        }
    }

    private NewsDetailsPage publishNews(String title, String content) {
        return new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(title)
                .enterContent(content)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .publish()
                .open()
                .openSearch()
                .searchNews(title)
                .openNewsByTitle(title);
    }
}
