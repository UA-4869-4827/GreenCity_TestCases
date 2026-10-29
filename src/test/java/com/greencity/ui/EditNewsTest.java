package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("UI")
@Feature("Eco News")
@DisplayName("Edit news")
public class EditNewsTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_CONTENT =
            "TestTest content for the edit news test, TestTest content for the edit news test.";

    private final String newsTitle = "TestTest edit " + System.currentTimeMillis();

    private boolean newsPublished = false;

    private NewsDetailsPage createAndOpenNews() {
        new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(newsTitle)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent(NEWS_CONTENT)
                .publish();
        newsPublished = true;

        return new EcoNewsPage(driver)
                .openSearch()
                .searchNews(newsTitle)
                .openNewsByTitle(newsTitle);
    }

    @AfterEach
    void deleteCreatedNews() {
        if (!newsPublished) {
            return;
        }
        new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(newsTitle)
                .openNewsByTitle(newsTitle)
                .deleteNews();
    }

    @Test
    @Story("Author sees the Edit button on own news")
    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Edit button is visible to the author")
    @Description("The author of a published news article sees the Edit button on the news details page.")
    @Issue("104")
    void editButtonIsVisibleToAuthor() {
        NewsDetailsPage news = createAndOpenNews();

        assertTrue(news.isEditDisplayed(), "Edit button is not visible to the author");
    }

    @Test
    @Story("Author opens the edit form of own news")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("Edit form is pre-filled with the news data")
    @Description("The edit form opens with the title, content and tags of the news; author and date are shown but not editable.")
    @Issue("107")
    void editFormIsPreFilledWithNewsData() {
        CreateNewsPage editForm = createAndOpenNews().editNews();

        assertAll("Edit form is opened",
                () -> assertTrue(editForm.isFormDisplayed(), "Edit form is not displayed"),
                () -> assertEquals("Edit news", editForm.getPageHeadingText(),
                        "Page heading is not 'Edit news'"));

        assertAll("Edit form is pre-filled",
                () -> assertEquals(newsTitle, editForm.getTitleValue(),
                        "Title is not pre-filled with the news title"),
                () -> assertEquals(NEWS_CONTENT, editForm.getContentText(),
                        "Content is not pre-filled with the news content"),
                () -> assertEquals(List.of(UiMessage.CREATE_NEWS_TAG_NEWS.text()),
                        editForm.getSelectedTags(),
                        "Selected tags do not match the tags of the news"),
                () -> assertEquals("", editForm.getSourceValue(),
                        "Source should be empty, the news was created without it"));

        assertAll("Author and date are shown and not editable",
                () -> assertFalse(editForm.getAuthorLabelText().replace("Author:", "").trim().isEmpty(),
                        "Author is not shown: " + editForm.getAuthorLabelText()),
                () -> assertFalse(editForm.getDateLabelText().isBlank(),
                        "Date label is empty"),
                () -> assertFalse(editForm.isAuthorEditable(),
                        "Author should not be an editable field"),
                () -> assertFalse(editForm.isDateEditable(),
                        "Date should not be an editable field"));
    }
}