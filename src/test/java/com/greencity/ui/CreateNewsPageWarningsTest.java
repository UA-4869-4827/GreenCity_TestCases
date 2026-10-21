package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateNewsPageWarningsTest extends AuthenticatedBaseTestRunner {

    private static final int TITLE_LIMIT = 170;
    private static final int CONTENT_MINIMUM = 20;
    private static final int CONTENT_LIMIT = 63_206;
    private static final String VALID_TITLE = "Test News";

    // [Create News] Title, Main Text and Source validation
    // #97, #100, #101

    @Test
    void titleFieldValidation() {
        CreateNewsPage createNews = openCreateNewsForm();

        createNews.enterTitle("").focusSourceField();

        boolean emptyTitleIsInvalid = createNews.isTitleInvalid();
        boolean publishDisabledWhileTitleEmpty = !createNews.isPublishButtonEnabled();
        boolean titleCounterDisplayed = createNews.isTitleCounterDisplayed();
        String emptyTitleCounter = createNews.getTitleCounterText();

        createNews.enterTitle("A".repeat(TITLE_LIMIT + 1));

        int titleLength = createNews.getTitleText().length();
        String overLimitCounter = createNews.getTitleCounterText();

        assertAll(
                () -> assertTrue(emptyTitleIsInvalid,
                        "Title field should be highlighted in red when empty"),
                () -> assertTrue(publishDisabledWhileTitleEmpty,
                        "Publish button should be disabled when Title is empty"),
                () -> assertTrue(titleCounterDisplayed,
                        "Title character counter should be displayed"),
                () -> assertEquals(UiMessage.CREATE_NEWS_TITLE_FIELD_INFO.text(), emptyTitleCounter),
                () -> assertEquals(TITLE_LIMIT, titleLength,
                        "Title should be truncated to 170 characters"),
                () -> assertEquals(TITLE_LIMIT + "/170", overLimitCounter,
                        "Title counter should show the 170 character limit"));

        createNews.enterTitle(VALID_TITLE);

        assertEquals("9/170", createNews.getTitleCounterText());
        assertFalse(createNews.isTitleInvalid(),
                "Title field should not be highlighted in red with a valid title");
        assertFalse(createNews.isPublishButtonEnabled(),
                "Publish button should remain disabled while Main Text is empty");

        createNews.selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent("This is test news content.");

        assertTrue(createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Title, Content and at least one tag are provided");
    }

    @Test
    void sourceFieldValidation() {
        CreateNewsPage createNews = openCreateNewsForm();
        fillMandatoryFields(createNews);

        assertTrue(createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Source is empty and all mandatory fields are filled");

        EcoNewsPage publishedWithoutSource = createNews.publish();
        assertPublished(publishedWithoutSource);

        createNews = publishedWithoutSource.createNews();
        fillMandatoryFields(createNews);
        createNews.enterSource("www.example.com").focusTitleField();

        assertTrue(createNews.isSourceWarningDisplayed(),
                "Source warning should be displayed for an invalid URL");
        assertEquals(UiMessage.CREATE_NEWS_SOURCE_HINT.text(), createNews.getSourceWarningText());
        assertTrue(createNews.isSourceWarningRed(),
                "Source warning should be highlighted in red");
        assertFalse(createNews.isPublishButtonEnabled(),
                "Publish button should remain disabled when Source contains an invalid URL");

        createNews.enterSource("https://example.com").focusTitleField();

        assertFalse(createNews.isSourceWarningDisplayed(),
                "Source warning should disappear for a valid URL");
        assertTrue(createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Source contains a valid URL");

        assertPublished(createNews.publish());
    }

    @Test
    void mainFieldValidation() {
        CreateNewsPage createNews = openCreateNewsForm();

        createNews.enterContent("Short text")
                .enterTitle("Test")
                .focusSourceField();

        assertEquals(UiMessage.CREATE_NEWS_MAIN_CONTENT_HINT.text(), createNews.getContentInfoText());
        assertTrue(createNews.isContentInfoWarning(),
                "Content warning should be red when Main Text is shorter than 20 characters");
        assertFalse(createNews.isPublishButtonEnabled(),
                "Publish button should be disabled when Main Text is shorter than 20 characters");

        createNews.enterContent("A".repeat(CONTENT_LIMIT + 1));

        assertEquals(CONTENT_LIMIT, createNews.getContentText().length(),
                "Main Text should be truncated to 63,206 characters");

        createNews.enterContent("A".repeat(CONTENT_MINIMUM)).focusSourceField();

        assertFalse(createNews.isContentInfoWarning(),
                "Content warning should disappear when Main Text has 20 characters");

        createNews.selectTag(UiMessage.CREATE_NEWS_TAG_NEWS);

        assertTrue(createNews.isPublishButtonEnabled(),
                "Publish button should be enabled when Main Text has at least 20 characters");
        assertPublished(createNews.publish());
    }

    private CreateNewsPage openCreateNewsForm() {
        return profilePage.getHeader().openEcoNews().createNews();
    }

    private void fillMandatoryFields(CreateNewsPage createNews) {
        createNews.enterTitle(VALID_TITLE)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent("This is test news content.");
    }

    private void assertPublished(EcoNewsPage ecoNewsPage) {
        assertFalse(driver.getCurrentUrl().contains("/create"),
                "Publishing should leave the create-news form");
        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), ecoNewsPage.getPageHeadingText());
    }
}
