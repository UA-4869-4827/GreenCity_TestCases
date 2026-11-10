package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Eco News")
@Feature("Delete News")
public class DeleteNewsTest extends AuthenticatedBaseTestRunner {

    private static final String NOT_FOUND_MESSAGE = "The Eco-news was not found";

    private long createdNewsId;

    @Test
    @DisplayName("User can delete own news")
    @Story("Delete own news")
    @Severity(SeverityLevel.CRITICAL)
    @Description("User publishes a news, deletes it after confirmation, "
            + "and the news is no longer available.")
    void userShouldBeAbleToDeleteOwnNews() {
        String title = "Delete News Test " + System.nanoTime();
        NewsDetailsPage newsDetailsPage = publishNews(title, "Test content for news deletion");
        long newsId = newsDetailsPage.getNewsId();
        createdNewsId = newsId;

        Allure.step("Open delete confirmation dialog", () -> {
            newsDetailsPage.openDeleteConfirmation();
            assertTrue(newsDetailsPage.isDeleteConfirmationDisplayed(),
                    "The deletion confirmation dialog is not displayed");
        });

        EcoNewsPage ecoNewsAfterDelete = Allure.step("Confirm news deletion", () ->
                newsDetailsPage.confirmDelete());
        createdNewsId = 0;

        Allure.step("Verify news is removed from the Eco news list", () -> {
            assertTrue(ecoNewsAfterDelete.isNewsListOpened(),
                    "The user was not redirected to the Eco news list");
            assertFalse(ecoNewsAfterDelete.isNewsDisplayedByTitle(title),
                    "The deleted news is still displayed in the list");
        });

        Allure.step("Open deleted news by URL and verify 'not found' message", () -> {
            ecoNewsAfterDelete.openDeletedNewsUrl(newsId);

            assertTrue(ecoNewsAfterDelete.isNewsListOpened(),
                    "The deleted news URL did not stay on the Eco news list");
            assertEquals(NOT_FOUND_MESSAGE, ecoNewsAfterDelete.getNewsNotFoundMessageText(),
                    "The missing-news message was not displayed");
        });
    }

    @Test
    @DisplayName("User can cancel news deletion")
    @Story("Cancel news deletion")
    @Severity(SeverityLevel.NORMAL)
    @Description("User opens the delete confirmation, cancels it, and the news stays available.")
    void userShouldBeAbleToCancelNewsDeletion() {
        String title = "Cancel Delete News Test " + System.nanoTime();
        NewsDetailsPage newsDetailsPage = publishNews(title, "Test content for cancelling news deletion");
        createdNewsId = newsDetailsPage.getNewsId();

        Allure.step("Open delete confirmation dialog", () -> {
            newsDetailsPage.openDeleteConfirmation();
            assertTrue(newsDetailsPage.isDeleteConfirmationDisplayed(),
                    "The deletion confirmation dialog is not displayed");
        });

        Allure.step("Cancel deletion and verify user stays on the news details page", () -> {
            newsDetailsPage.cancelDelete();

            assertEquals(title, newsDetailsPage.getTitleText(),
                    "The user did not remain on the news details page");
        });

        Allure.step("Reopen the news and verify it is still available", () -> {
            newsDetailsPage.open(createdNewsId);
            assertEquals(title, newsDetailsPage.getTitleText(),
                    "The news is not available after refresh");
        });
    }

    @AfterEach
    void deleteCreatedNews() {
        if (createdNewsId > 0) {
            Allure.step("Cleanup: delete created news with id " + createdNewsId, () -> {
                new NewsDetailsPage(driver).open(createdNewsId).deleteNews();
                createdNewsId = 0;
            });
        }
    }

    private NewsDetailsPage publishNews(String title, String content) {
        return Allure.step("Precondition: publish news '" + title + "'", () ->
                new EcoNewsPage(driver)
                        .open()
                        .createNews()
                        .enterTitle(title)
                        .enterContent(content)
                        .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                        .publish()
                        .open()
                        .openSearch()
                        .searchNews(title)
                        .openNewsByTitle(title));
    }
}