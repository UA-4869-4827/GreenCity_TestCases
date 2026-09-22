package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.page.econews.PreviewNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NewsPreviewTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_CONTENT =
            "TestContent TestContent TestContent TestContent TestContent TestContent.";

    private final String newsTitle = "TestTitle " + System.currentTimeMillis();

    private boolean newsPublished = false;

    private CreateNewsPage openFilledCreateForm() {
        return new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(newsTitle)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent(NEWS_CONTENT);
    }

    @AfterEach
    void deletePublishedNews() {
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
    void previewShowsEnteredContent() {
        PreviewNewsPage preview = openFilledCreateForm().preview();

        assertTrue(preview.isOpened(), "Preview page did not open");

        assertAll("Preview shows the entered news",
                () -> assertEquals(newsTitle, preview.getTitleText(),
                        "Preview shows a different title"),
                () -> assertEquals(NEWS_CONTENT, preview.getContentText(),
                        "Preview shows different content"),
                () -> assertEquals(List.of(UiMessage.CREATE_NEWS_TAG_NEWS.text()), preview.getTagTexts(),
                        "Preview shows different tags"));
    }

    @Test
    void backToEditingReturnsFilledForm() {
        CreateNewsPage form = openFilledCreateForm()
                .preview()
                .clickBackToEditing();

        assertTrue(form.isFormDisplayed(),
                "Create news form is not displayed after 'Back to editing'");

        assertAll("Form keeps the entered data",
                () -> assertEquals(newsTitle, form.getTitleValue(),
                        "Title was lost after returning from preview"),
                () -> assertEquals(NEWS_CONTENT, form.getContentText(),
                        "Content was lost after returning from preview"));
    }

    @Test
    void publishFromPreviewAddsNewsToList() {
        EcoNewsPage newsList = openFilledCreateForm()
                .preview()
                .publish();
        newsPublished = true;

        assertTrue(newsList.isNewsListOpened(), "News list did not open after publishing");

        NewsDetailsPage publishedNews = newsList
                .openSearch()
                .searchNews(newsTitle)
                .openNewsByTitle(newsTitle);


        assertEquals(newsTitle, publishedNews.getTitleText(),
                "Published news has a different title");
    }
}