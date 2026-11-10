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
        LOG.info("START TEST: User deletes own news");

        String title = "Delete News Test " + System.nanoTime();
        NewsDetailsPage newsDetailsPage = publishNews(title, "Test content for news deletion");
        long newsId = newsDetailsPage.getNewsId();
        createdNewsId = newsId;
        LOG.info("News created: id={}, title='{}'", newsId, title);

        LOG.info("Opening delete confirmation dialog");
        newsDetailsPage.openDeleteConfirmation();
        assertTrue(newsDetailsPage.isDeleteConfirmationDisplayed(),
                "The deletion confirmation dialog is not displayed");

        LOG.info("Confirming deletion of news id={}", newsId);
        EcoNewsPage ecoNewsAfterDelete = newsDetailsPage.confirmDelete();
        createdNewsId = 0;

        LOG.info("Verifying redirect to Eco news list and that news is not displayed");
        assertTrue(ecoNewsAfterDelete.isNewsListOpened(),
                "The user was not redirected to the Eco news list");
        assertFalse(ecoNewsAfterDelete.isNewsDisplayedByTitle(title),
                "The deleted news is still displayed in the list");

        LOG.info("Opening URL of the deleted news id={}", newsId);
        ecoNewsAfterDelete.openDeletedNewsUrl(newsId);

        assertTrue(ecoNewsAfterDelete.isNewsListOpened(),
                "The deleted news URL did not stay on the Eco news list");
        String actualMessage = ecoNewsAfterDelete.getNewsNotFoundMessageText();
        LOG.debug("Not-found message actual='{}'", actualMessage);
        assertEquals(NOT_FOUND_MESSAGE, actualMessage,
                "The missing-news message was not displayed");

        LOG.info("END TEST: passed");
    }

    @Test
    void userShouldBeAbleToCancelNewsDeletion() {
        LOG.info("START TEST: User cancels news deletion");

        String title = "Cancel Delete News Test " + System.nanoTime();
        NewsDetailsPage newsDetailsPage = publishNews(title, "Test content for cancelling news deletion");
        createdNewsId = newsDetailsPage.getNewsId();
        LOG.info("News created: id={}, title='{}'", createdNewsId, title);

        LOG.info("Opening delete confirmation dialog");
        newsDetailsPage.openDeleteConfirmation();
        assertTrue(newsDetailsPage.isDeleteConfirmationDisplayed(),
                "The deletion confirmation dialog is not displayed");

        LOG.info("Clicking cancel in the delete dialog");
        newsDetailsPage.cancelDelete();

        assertEquals(title, newsDetailsPage.getTitleText(),
                "The user did not remain on the news details page");

        LOG.info("Reopening news id={} to verify it still exists", createdNewsId);
        newsDetailsPage.open(createdNewsId);
        assertEquals(title, newsDetailsPage.getTitleText(),
                "The news is not available after refresh");

        LOG.info("END TEST: passed");
    }

    @AfterEach
    void deleteCreatedNews() {
        if (createdNewsId > 0) {
            LOG.info("Cleanup: deleting news id={}", createdNewsId);
            new NewsDetailsPage(driver).open(createdNewsId).deleteNews();
            createdNewsId = 0;
        } else {
            LOG.debug("Cleanup: nothing to delete");
        }
    }

    private NewsDetailsPage publishNews(String title, String content) {
        LOG.info("Publishing news: title='{}'", title);
        NewsDetailsPage page = new EcoNewsPage(driver)
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
        LOG.info("News published and opened: title='{}'", title);
        return page;
    }
}