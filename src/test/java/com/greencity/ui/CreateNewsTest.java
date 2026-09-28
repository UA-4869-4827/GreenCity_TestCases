package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateNewsTest extends AuthenticatedBaseTestRunner {

    // [Create News][Test Case] Cancel Button Behavior
    // #102

    @Test
    void cancelButtonShouldShowConfirmationAndReturnToEcoNews() {
        EcoNewsPage ecoNewsPage = profilePage.getHeader().openEcoNews();

        CreateNewsPage createNewsPage = ecoNewsPage.createNews();

        createNewsPage.cancel();

        assertTrue(createNewsPage.isCancelConfirmationDisplayed());

        EcoNewsPage ecoNewsAfterCancel = createNewsPage.confirmCancel();

        assertEquals(
                UiMessage.NEWS_PAGE_HEADER.text(),
                ecoNewsAfterCancel.getPageHeadingText());
    }
}
