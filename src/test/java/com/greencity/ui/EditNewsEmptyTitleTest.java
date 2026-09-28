package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditNewsEmptyTitleTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_CONTENT =
            "A news article created for the empty title validation test. Its title must stay unchanged.";

    private final String newsTitle = "Empty title validation " + System.nanoTime();
    private long createdNewsId;

    @Test
    void authorCannotSubmitNewsWithoutTitle() {
        new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(newsTitle)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent(NEWS_CONTENT)
                .publish();

        NewsDetailsPage article = new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(newsTitle)
                .openNewsByTitle(newsTitle);
        createdNewsId = article.getNewsId();

        CreateNewsPage editForm = article.editNews();
        assertEquals(newsTitle, editForm.getTitleValue(), "The article title was not loaded into the edit form");
        String editFormUrl = editForm.getFormUrl();

        editForm.clearTitle();
        assertTrue(editForm.isTitleBorderRed(), "The empty title field does not have a solid red border");
        assertTrue(editForm.isEditButtonDisabled(), "The Edit button is enabled for an empty title");

        editForm.attemptEdit();
        assertEquals(editFormUrl, editForm.getFormUrl(), "The edit form was submitted");
        assertTrue(editForm.isFormDisplayed(), "The edit form closed after clicking the disabled button");

        NewsDetailsPage reloadedArticle = new NewsDetailsPage(driver).open(createdNewsId);
        assertEquals(newsTitle, reloadedArticle.getTitleText(), "The news title changed despite invalid input");
    }

    @AfterEach
    void deleteCreatedNews() {
        if (createdNewsId > 0) {
            new NewsDetailsPage(driver).open(createdNewsId).deleteNews();
        }
    }
}
