package com.greencity.ui.tests;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static com.greencity.ui.locale.UiMessage.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserCreateNewsTests extends AuthenticatedBaseTestRunner {

    private static final List<String> EXPECTED_TAGS =
            List.of(NEWS_TAG_NEWS.text(),
                    NEWS_TAG_EVENTS.text(),
                    NEWS_TAG_EDUCATION.text(),
                    NEWS_TAG_INITIATIVES.text(),
                    NEWS_TAG_ADS.text());

    private CreateNewsPage createNewsPage;
    private String correctNewsTitle = "Coffee takeaway with 20% discount";
    private String correctNewsContent = "This is a valid content string with more than twenty characters.";

    @BeforeEach
    void beforeCreateNewsTest() {
        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();
        createNewsPage = ecoNewsPage.openCreateNewsRoute().createNews();
    }

    @Test
    void createNewsFormShouldDisplayAllRequiredFieldsInCorrectOrder() {

        assertTrue(createNewsPage.isTitleInputVisible(), "Title field is not visible");

        assertTrue(createNewsPage.isTagSectionVisible(), "Tag section is not visible");

        assertTrue(createNewsPage.isAddImageVisible(), "Add Image field is not visible");

        assertTrue(createNewsPage.isMainTextVisible(), "Main Text field is not visible");

        assertTrue(createNewsPage.isAuthorVisible(), "Author field is not visible");

        assertTrue(createNewsPage.isDateVisible(), "Date field is not visible");

        assertTrue(createNewsPage.isSourceInputVisible(), "Source field is not visible");

        assertTrue(createNewsPage.isCancelButtonVisible(), "Cancel button is not visible");

        assertTrue(createNewsPage.isPreviewButtonVisible(), "Preview button is not visible");

        assertTrue(createNewsPage.isPublishButtonVisible(), "Publish button is not visible");
    }

    @Test
    void titleFieldShouldHaveCorrectPlaceholderAndCharacterCounter() {
        assertEquals(CREATE_NEWS_TITLE_FIELD_PLACEHOLDER.text(), createNewsPage.getTitlePlaceholder(),
                "Title placeholder text is incorrect");
        assertEquals(UiMessage.CREATE_NEWS_TITLE_FIELD_INFO.text(), createNewsPage.getTitleCounterText().trim(), "Title character counter should start at 0/170");
    }


    @Test
    void tagsSectionShouldContainExactlyFiveTagsInCorrectOrder() {
        assertEquals(5, createNewsPage.getTagsCount(), "There should be exactly 5 tags");
        assertEquals(EXPECTED_TAGS, createNewsPage.getTagNames(), "Tags are missing, extra, or in the wrong order");
    }


    @Test
    void tagsSectionShouldDisplayThreeTagsLimitHint() {
        assertEquals(CREATE_NEWS_TAGS_INFO.text(), createNewsPage.getTagsLimitHintText().trim(), "Tags limit hint text is incorrect or missing");
    }


    @Test
    void contentFieldShouldHaveCorrectPlaceholderAndCounterHint() {
        assertEquals(UiMessage.CREATE_NEWS_MAIN_CONTENT_PLACEHOLDER.text(),
                createNewsPage.getContentPlaceholder(), "Content placeholder text is incorrect");
        assertEquals(UiMessage.CREATE_NEWS_MAIN_CONTENT_HINT.text(),
                createNewsPage.getContentCounterHintText().trim(), "Content length hint text is incorrect");
    }


    @Test
    void sourceFieldShouldHaveCorrectPlaceholderAndHintText() {
        assertEquals(CREATE_NEWS_SOURCE_PLACEHOLDER.text(), createNewsPage.getSourcePlaceholder(), "Source placeholder text is incorrect");
        assertEquals(CREATE_NEWS_SOURCE_HINT.text(), createNewsPage.getSourceHintText().trim(), "Source hint text is incorrect");
    }


    @Test
    void authorFieldShouldBePrefilledAndNotEditable() {
        assertFalse(createNewsPage.getAuthorValueText().isEmpty(), "Author field should be pre-filled with the logged-in user's name");
        assertFalse(createNewsPage.isAuthorFieldEditable(), "Author field must not be an editable input/textarea");
    }

    @Test
    void authorFieldShouldMatchRegisteredUserFullName() {
        String expectedFullName = testValueProvider.getUserName();
        String actualAuthorText = createNewsPage.getAuthorValueText();
        assertEquals(expectedFullName, actualAuthorText, "Author field should display the registered user's full name ('" + expectedFullName + "'), but was: '" + actualAuthorText + "'");
    }


    @Test
    void dateFieldShouldBePrefilledWithCurrentDateAndNotEditable() {
        String expectedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM d, yyyy", Locale.ENGLISH));

        assertEquals(expectedDate, createNewsPage.getDateValueText(), "Date field should be pre-filled with today's date");
        assertFalse(createNewsPage.isDateFieldEditable(), "Date field must not be an editable input/textarea");
    }

    @Test
    void pictureUploadAreaShouldDisplayFileRequirementsWarning() {
        assertEquals(UiMessage.CREATE_NEWS_PICTURE_UPLOAD_REQUIREMENT.text(),
                createNewsPage.getPictureWarningText().trim(), "Picture upload requirements text is incorrect");
        assertTrue(createNewsPage.isBrowseLinkVisible(), "'browse' link should be visible");
    }

    @Test
    void publishButtonShouldBeDisabledByDefault() {
        assertFalse(createNewsPage.isPublishButtonEnabled(), "Publish button should be disabled until the form is valid");
    }

    @Test
    void publishButtonShouldBeEnabledAfterFillingRequiredFields() {
        createNewsPage.enterTitle(correctNewsTitle)
                .selectTag(NEWS_TAG_NEWS)
                .enterContent(correctNewsContent);

        assertTrue(createNewsPage.isPublishButtonEnabled(), "Publish button should become enabled once required fields are valid");
    }

}
