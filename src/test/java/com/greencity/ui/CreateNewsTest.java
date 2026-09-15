package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.profile.ProfilePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CreateNewsTest extends BaseTestRunner {

    // [Create News][Test Case] Cancel Button Behavior
    // #102

    @Test
    void cancelButtonShouldShowConfirmationAndReturnToEcoNews() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        ProfilePage profilePage = signInModal.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword(),
                ProfilePage.class);

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