package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateNewsTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_TITLE = "Test";
    private static final String NEWS_CONTENT = "Test content with 20 chars";

    // [Create News][Test Case] Cancel Button Behavior
    // #102

    @Test
    void cancelShouldWarnAboutLostContentAndCanKeepOrDiscardTheDraft() {
        CreateNewsPage createNewsPage = profilePage.getHeader()
                .openEcoNews()
                .createNews();

        createNewsPage.enterTitle(NEWS_TITLE)
                .enterContent(NEWS_CONTENT)
                .cancel();

        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_TITLE.text(),
                createNewsPage.getCancelWarningTitleText());
        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_SUBTITLE.text(),
                createNewsPage.getCancelWarningSubtitleText());
        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_CONTINUE.text(),
                createNewsPage.getContinueEditingButtonText());
        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_CONFIRM.text(),
                createNewsPage.getConfirmCancelButtonText());

        createNewsPage.continueEditing();

        assertFalse(createNewsPage.isCancelConfirmationDisplayed());
        assertEquals(NEWS_TITLE, createNewsPage.getTitleValue());
        assertEquals(NEWS_CONTENT, createNewsPage.getContentText().trim());

        EcoNewsPage ecoNewsPage = createNewsPage.cancel().confirmCancel();

        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), ecoNewsPage.getPageHeadingText());
    }
}
