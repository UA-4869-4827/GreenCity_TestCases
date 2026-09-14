package com.greencity.ui.smoke;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EcoNewsNavigationTest extends BaseTestRunner {
    @Test
    @DisplayName("TC-P0-NEWS-01 – Eco news landing from header and Home")
    void testEcoNewsLandingPage() {

        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        assertEcoNewsLandingPage(ecoNewsPage);

        homePage = ecoNewsPage.getHeader().clickLogo();

        ecoNewsPage = homePage.openAllNews();

        assertEcoNewsLandingPage(ecoNewsPage);
    }

    private void assertEcoNewsLandingPage(EcoNewsPage ecoNewsPage) {

        Assertions.assertTrue(
                ecoNewsPage.isPageHeadingDisplayed(),
                "Eco News heading is not displayed"
        );

        Assertions.assertTrue(
                ecoNewsPage.isItemsFoundCounterDisplayed(),
                "Items found counter is not displayed"
        );

        Assertions.assertTrue(
                ecoNewsPage.isNewsCardDisplayed(),
                "News card is not displayed"
        );

        Assertions.assertTrue(
                ecoNewsPage.getViewModeToggle().isTableViewActive(),
                "Gallery view is not active by default"
        );
    }

    @Test
    @DisplayName("TC-P0-NEWS-02 – Guest cannot open Create news and is gated on saved news")
    void testGuestCannotCreateNewsAndSavedNewsRequiresSignIn() {

        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        Assertions.assertFalse(
                ecoNewsPage.isCreateNewsButtonDisplayed(),
                "Create news button is displayed for guest"
        );

        SignInModal signInModal = ecoNewsPage.openSavedNewsAsGuest();

        Assertions.assertEquals(
                "Welcome back!",
                signInModal.getModalTitleText(),
                "Sign in modal is not displayed"
        );
    }
}
