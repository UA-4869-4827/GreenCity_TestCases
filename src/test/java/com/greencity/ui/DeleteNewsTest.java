package com.greencity.ui;

import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteNewsTest extends AuthenticatedBaseTestRunner {

    // [Delete News][Test Case] Successfully Delete News #125

    @Test
    void userShouldBeAbleToDeleteOwnNews() {
        EcoNewsPage ecoNewsPage = profilePage.getHeader().openEcoNews();

        CreateNewsPage createNewsPage = ecoNewsPage.createNews();

        String title = "Delete News Test";

        createNewsPage
                .enterTitle(title)
                .enterContent("Test content for news deletion")
                .selectTag("News");

        EcoNewsPage ecoNewsAfterPublish = createNewsPage.publish();

        NewsDetailsPage newsDetailsPage =
                ecoNewsAfterPublish.openNewsByTitle(title);

        long newsId = newsDetailsPage.getNewsId();

        EcoNewsPage ecoNewsAfterDelete = newsDetailsPage.deleteNews();

        assertTrue(ecoNewsAfterDelete.isNewsListOpened());

        assertFalse(
                ecoNewsAfterDelete.isNewsDisplayedByTitle(title));

        ecoNewsAfterDelete.openDeletedNewsUrl(newsId);

        assertTrue(ecoNewsAfterDelete.isNewsListOpened());

        assertTrue(
                ecoNewsAfterDelete.isNewsNotFoundMessageDisplayed());
    }

    // [Delete News][Test Case] Cancel News Deletion #126

    @Test
    void userShouldBeAbleToCancelNewsDeletion() {
        EcoNewsPage ecoNewsPage = profilePage.getHeader().openEcoNews();

        CreateNewsPage createNewsPage = ecoNewsPage.createNews();

        String title = "Cancel Delete News Test";

        createNewsPage
                .enterTitle(title)
                .enterContent("Test content for cancelling news deletion")
                .selectTag("News");

        EcoNewsPage ecoNewsAfterPublish = createNewsPage.publish();

        NewsDetailsPage newsDetailsPage =
                ecoNewsAfterPublish.openNewsByTitle(title);

        newsDetailsPage.cancelDelete();

        assertEquals(title, newsDetailsPage.getTitleText());

        newsDetailsPage.open(newsDetailsPage.getNewsId());

        assertEquals(title, newsDetailsPage.getTitleText());
    }
}