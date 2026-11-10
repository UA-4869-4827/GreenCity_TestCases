package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Eco News")
@Feature("Create News")
public class CreateNewsTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_TITLE = "Test";
    private static final String NEWS_CONTENT = "Test content with 20 chars";

    @Test
    @DisplayName("Cancel button: warning, keep draft, discard draft")
    @Story("Cancel Button Behavior")
    @Severity(SeverityLevel.NORMAL)
    @Description("Cancel shows a warning; the user can keep editing or discard the draft.")
    void cancelShouldWarnAboutLostContentAndCanKeepOrDiscardTheDraft() {
        CreateNewsPage createNewsPage = Allure.step("Open Create News page", () ->
                profilePage.getHeader()
                        .openEcoNews()
                        .createNews());

        Allure.step("Fill title and content, then click Cancel", () -> {
            createNewsPage.enterTitle(NEWS_TITLE)
                    .enterContent(NEWS_CONTENT)
                    .cancel();
        });

        Allure.step("Verify cancel warning texts", () -> {
            assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_TITLE.text(),
                    createNewsPage.getCancelWarningTitleText());
            assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_SUBTITLE.text(),
                    createNewsPage.getCancelWarningSubtitleText());
            assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_CONTINUE.text(),
                    createNewsPage.getContinueEditingButtonText());
            assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_CONFIRM.text(),
                    createNewsPage.getConfirmCancelButtonText());
        });

        Allure.step("Click 'Continue editing'", () -> {
            createNewsPage.continueEditing();
        });

        Allure.step("Verify draft is kept after continuing editing", () -> {
            assertFalse(createNewsPage.isCancelConfirmationDisplayed());
            assertEquals(NEWS_TITLE, createNewsPage.getTitleValue());
            assertEquals(NEWS_CONTENT, createNewsPage.getContentText().trim());
        });

        EcoNewsPage ecoNewsPage = Allure.step("Click Cancel and confirm discarding the draft", () ->
                createNewsPage.cancel().confirmCancel());

        Allure.step("Verify Eco News page is opened", () -> {
            assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), ecoNewsPage.getPageHeadingText());
        });
    }
}