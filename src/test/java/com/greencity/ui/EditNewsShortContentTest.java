package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI")
@Feature("Eco News")
@DisplayName("Edit news - content length validation")
public class EditNewsShortContentTest extends AuthenticatedBaseTestRunner {

    private static final String ORIGINAL_CONTENT =
            "Original article content is long enough to meet the minimum length requirement.";
    private static final String SHORT_CONTENT = "Too short";

    private final String newsTitle = "Short content validation " + System.nanoTime();
    private long createdNewsId;

    @Test
    @Story("Author cannot save news with content shorter than 20 characters")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Content shorter than 20 characters blocks submitting the edit form")
    @Description("Entering content under the 20-character minimum shows the validation hint and disables Edit; the saved content stays unchanged.")
    @Issue("109")
    void authorCannotSubmitNewsWithContentShorterThanTwentyCharacters() {
        new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(newsTitle)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent(ORIGINAL_CONTENT)
                .publish();

        NewsDetailsPage article = new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(newsTitle)
                .openNewsByTitle(newsTitle);
        createdNewsId = article.getNewsId();

        CreateNewsPage editForm = article.editNews();
        assertEquals(ORIGINAL_CONTENT, editForm.getContentText(),
                "The existing article content was not loaded into the edit form");
        String editFormUrl = editForm.getFormUrl();

        editForm.enterContent(SHORT_CONTENT);
        assertEquals(SHORT_CONTENT, editForm.getContentText(), "The short content was not entered");
        assertTrue(editForm.isContentLengthErrorDisplayed(),
                "The content minimum length validation error is not visible");
        assertTrue(editForm.getContentCounterHintText().contains("20"),
                "The validation message does not state the 20-character minimum");
        assertTrue(editForm.isEditButtonDisabled(), "Submit is enabled for content shorter than 20 characters");

        editForm.attemptEdit();
        assertEquals(editFormUrl, editForm.getFormUrl(), "The invalid edit form was submitted");
        assertTrue(editForm.isFormDisplayed(), "The edit form closed after the disabled Submit was clicked");

        editForm.cancel();
        if (editForm.isCancelConfirmationDisplayed()) {
            editForm.confirmCancel();
        }

        NewsDetailsPage reloadedArticle = new NewsDetailsPage(driver).open(createdNewsId);
        assertEquals(ORIGINAL_CONTENT, reloadedArticle.getBodyText(),
                "The saved news content changed despite the validation error");
    }

    @AfterEach
    void deleteCreatedNews() {
        if (createdNewsId > 0) {
            new NewsDetailsPage(driver).open(createdNewsId).deleteNews();
        }
    }
}
