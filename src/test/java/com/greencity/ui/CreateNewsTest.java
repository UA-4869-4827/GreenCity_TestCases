package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CreateNewsTest extends AuthenticatedBaseTestRunner {

    private static final Logger LOG = LoggerFactory.getLogger(CreateNewsTest.class);

    private static final String NEWS_TITLE = "Test";
    private static final String NEWS_CONTENT = "Test content with 20 chars";


    @Test
    void cancelShouldWarnAboutLostContentAndCanKeepOrDiscardTheDraft() {
        LOG.info("START TEST: Cancel button behavior on Create News page (#102)");

        LOG.info("Opening Eco News page and clicking 'Create news'");
        CreateNewsPage createNewsPage = profilePage.getHeader()
                .openEcoNews()
                .createNews();

        LOG.info("Entering title='{}' and content='{}'", NEWS_TITLE, NEWS_CONTENT);
        createNewsPage.enterTitle(NEWS_TITLE)
                .enterContent(NEWS_CONTENT);

        LOG.info("Clicking 'Cancel' button");
        createNewsPage.cancel();

        LOG.info("Verifying cancel warning texts");
        String actualTitle = createNewsPage.getCancelWarningTitleText();
        LOG.debug("Warning title actual='{}'", actualTitle);
        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_TITLE.text(), actualTitle);

        String actualSubtitle = createNewsPage.getCancelWarningSubtitleText();
        LOG.debug("Warning subtitle actual='{}'", actualSubtitle);
        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_SUBTITLE.text(), actualSubtitle);

        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_CONTINUE.text(),
                createNewsPage.getContinueEditingButtonText());
        assertEquals(UiMessage.CREATE_NEWS_CANCEL_WARNING_CONFIRM.text(),
                createNewsPage.getConfirmCancelButtonText());

        LOG.info("Clicking 'Continue editing' and verifying draft is kept");
        createNewsPage.continueEditing();

        assertFalse(createNewsPage.isCancelConfirmationDisplayed(),
                "Cancel confirmation should be closed after 'Continue editing'");
        assertEquals(NEWS_TITLE, createNewsPage.getTitleValue());
        assertEquals(NEWS_CONTENT, createNewsPage.getContentText().trim());

        LOG.info("Cancelling again and confirming discard of the draft");
        EcoNewsPage ecoNewsPage = createNewsPage.cancel().confirmCancel();

        String heading = ecoNewsPage.getPageHeadingText();
        LOG.info("Redirected to Eco News page, heading='{}'", heading);
        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), heading);

        LOG.info("END TEST: passed");
    }
}