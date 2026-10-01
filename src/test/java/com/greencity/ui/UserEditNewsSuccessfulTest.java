package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//[Edit News][Test Case]Successful news editing by the author #106

public class UserEditNewsSuccessfulTest extends AuthenticatedBaseTestRunner {
    private static final String NEWS_CONTENT =
            "TestTest content for the edit news test, TestTest content for the edit news test.";

    private static final long TIMESTAMP = System.currentTimeMillis();

    private final String NEWS_TITLE = "TestTest edit " + TIMESTAMP;
    private final String EDITED_TITLE = "TestTest edited " + TIMESTAMP;

    private static final String EDITED_CONTENT = "Edited content for the edit news test, Edited content for the edit news test.";

    private boolean newsPublished = false;
    private boolean newsEdited = false;

    private NewsDetailsPage createAndOpenNews() {
        new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(NEWS_TITLE)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent(NEWS_CONTENT)
                .publish();
        newsPublished = true;

        return new EcoNewsPage(driver)
                .openSearch()
                .searchNews(NEWS_TITLE)
                .openNewsByTitle(NEWS_TITLE);
    }

    @AfterEach
    void deleteCreatedNews() {
        if (!newsPublished) {
            return;
        }

        String titleToDelete = newsEdited ? EDITED_TITLE : NEWS_TITLE;
        new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(titleToDelete)
                .openNewsByTitle(titleToDelete)
                .deleteNews();
    }

    @Test
    void authorCanEditOwnNewsAndChangesAreSaved() {
        NewsDetailsPage news = createAndOpenNews();

        String originalCreatedDate = news.getDateText();

        CreateNewsPage editForm = news.editNews();

        editForm
                .enterTitle(EDITED_TITLE)
                .enterContent(EDITED_CONTENT)
                .selectTag(UiMessage.CREATE_NEWS_TAG_EVENTS)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .publish();
        newsEdited = true;

        NewsDetailsPage updatedNews = new EcoNewsPage(driver)
                .filterByEvents()
                .filterByNews()
                .openSearch()
                .searchNews(EDITED_TITLE)
                .openNewsByTitle(EDITED_TITLE);

        assertAll("News is updated",
                () -> assertEquals(EDITED_TITLE, updatedNews.getTitleText(), "News title was not updated"),
                () -> assertEquals(EDITED_CONTENT, updatedNews.getBodyText(), "News content was not updated"),
                () -> assertTrue(updatedNews.getBodyText().contains(EDITED_CONTENT), "Updated content is not displayed"),
                () -> assertFalse(updatedNews.getTagsText().contains(UiMessage.CREATE_NEWS_TAG_NEWS.text()), "Old 'News' tag is still present"),
                () -> assertTrue(updatedNews.getTagsText().contains(UiMessage.CREATE_NEWS_TAG_EVENTS.text()), "News tag 'Events' was not added"),
                () -> assertEquals(originalCreatedDate, updatedNews.getDateText(), "News creation date was changed after editing"));
    }
}
