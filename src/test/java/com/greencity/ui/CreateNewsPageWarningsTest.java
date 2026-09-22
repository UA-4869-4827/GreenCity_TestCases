package com.greencity.ui;

import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CreateNewsPageWarningsTest extends AuthenticatedBaseTestRunner {

    @Test
    void titleFieldValidation() {

        EcoNewsPage news = profilePage.getHeader().openEcoNews();
        CreateNewsPage createNews = news.createNews();

        createNews.enterTitle("");

        assertTrue(
                createNews.isTitleInvalid(),
                "Title field should be highlighted in red when empty"
        );

        assertFalse(
                createNews.isPublishButtonEnabled(),
                "Publish button should be disabled when Title is empty"
        );

        assertTrue(
                createNews.isTitleCounterDisplayed(),
                "Title character counter should be displayed"
        );

        assertEquals(
                "0/170",
                createNews.getTitleCounterText(),
                "Title counter should show 0/170"
        );

        String titleWith171Characters = "A".repeat(171);

        createNews.enterTitle(titleWith171Characters);

        assertEquals(
                "171/170",
                createNews.getTitleCounterText(),
                "Title counter should show 171/170"
        );

        assertTrue(
                createNews.isTitleCounterWarning(),
                "Title counter should be highlighted as warning when exceeding the limit"
        );

        assertTrue(
                createNews.getTitleText().length() <= 170,
                "Title should not contain more than 170 characters"
        );

        createNews.enterTitle("Test News");

        assertEquals(
                "9/170",
                createNews.getTitleCounterText(),
                "Title counter should show 9/170"
        );

        assertFalse(
                createNews.isTitleInvalid(),
                "Title field should not be highlighted in red with a valid title"
        );

        assertFalse(
                createNews.isPublishButtonEnabled(),
                "Publish button should remain disabled while Main Text is empty"
        );

        createNews.selectTag("News");

        createNews.enterContent("This is test news content.");

        assertTrue(
                createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Title, Content and at least one tag are provided"
        );
    }


    @Test
    void sourceFieldValidation() {

        EcoNewsPage news = profilePage.getHeader().openEcoNews();
        CreateNewsPage createNews = news.createNews();


        createNews.enterTitle("Test News");
        createNews.selectTag("News");
        createNews.enterContent("This is test news content.");

        assertTrue(
                createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Source is empty and all mandatory fields are filled"
        );

        createNews.enterSource("www.example.com");

        createNews.focusTitleField();

        assertTrue(
                createNews.isSourceWarningDisplayed(),
                "Source warning should be displayed for an invalid URL"
        );

        assertEquals(
                "Please add the link of original article/news/post. Link must start with http(s)://",
                createNews.getSourceWarningText(),
                "Source warning text should be correct"
        );

        assertTrue(
                createNews.isSourceWarningRed(),
                "Source warning should be highlighted in red"
        );

        assertFalse(
                createNews.isPublishButtonEnabled(),
                "Publish button should remain disabled when Source contains an invalid URL"
        );

        createNews.enterSource("https://example.com");

        createNews.focusTitleField();

        assertTrue(
                createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Source contains a valid URL"
        );

        EcoNewsPage publishedNews = createNews.publish();

        assertTrue(
                driver.getCurrentUrl().contains("/#/greenCity/news"),
                "User should be redirected to the EcoNews page after publishing"
        );
    }


    @Test
    void mainFieldValidation() {

        EcoNewsPage news = profilePage.getHeader().openEcoNews();
        CreateNewsPage createNews = news.createNews();

        createNews.enterContent("Short text");

        createNews.enterTitle("Test");

        createNews.focusSourceField();

        assertTrue(
                createNews.isContentInfoWarning(),
                "Content warning should be displayed when Main Text is shorter than 20 characters"
        );

        assertFalse(
                createNews.isPublishButtonEnabled(),
                "Publish button should be disabled when Main Text is shorter than 20 characters"
        );

        String contentWith63207Characters = "A".repeat(63207);
        createNews.enterContent(contentWith63207Characters);

        assertTrue(
                createNews.getContentText().length() <= 63206,
                "Main Text should not exceed 63,206 characters"
        );

        createNews.enterContent("This is a valid test content");

        createNews.focusSourceField();

        assertFalse(
                createNews.isContentInfoWarning(),
                "Content warning should disappear when Main Text is valid"
        );

        createNews.selectTag("News");

        assertTrue(
                createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Main Text is valid"
        );

        EcoNewsPage publishedNews = createNews.publish();

        assertTrue(
                driver.getCurrentUrl().contains("/#/greenCity/news"),
                "User should be redirected to the EcoNews page after publishing"
        );
    }
}

