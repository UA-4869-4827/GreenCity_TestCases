package com.greencity.header;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.places.PlacesPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HeaderTest extends BaseTestRunner {
    @Test
    @DisplayName("TC-P0-NAV-01 – Header opens GreenCity sections")
    void testHeaderNavigation() {
        homePage = homePage.getHeader()
                .clickSignIn()
                .signIn(
                        testValueProvider.getUserEmail(),
                        testValueProvider.getUserPassword(),
                        HomePage.class
                );

        EcoNewsPage ecoNewsPage = homePage.getHeader().openEcoNews();

        Assertions.assertTrue(
                ecoNewsPage.isPageHeadingDisplayed(),
                "Eco News page heading is not displayed"
        );

        EventsPage eventsPage = ecoNewsPage.getHeader().openEvents();

        Assertions.assertTrue(
                eventsPage.isPageHeadingDisplayed(),
                "Events page heading is not displayed"
        );

        PlacesPage placesPage = eventsPage.getHeader().openPlaces();

        Assertions.assertTrue(
                placesPage.isSearchForPlaceDisplayed(),
                "Searching for a place input is not displayed"
        );

        Assertions.assertTrue(
                placesPage.isAddPlaceButtonDisplayed(),
                "Add place button is not displayed"
        );

        AboutUsPage aboutUsPage = placesPage.getHeader().openAboutUs();

        Assertions.assertTrue(
                aboutUsPage.isPageHeadingDisplayed(),
                "About Us page heading is not displayed"
        );

        HomePage homePage = aboutUsPage.getHeader().clickLogo();

        Assertions.assertTrue(
                homePage.isMainTitleDisplayed(),
                "Home page main title is not displayed"
        );
    }

    @Test
    @DisplayName("TC-P0-NAV-02 – Guest My space is gated; UBS courier leaves GreenCity")
    void testGuestHeaderNavigation() {
        homePage.getHeader().clickMySpace();

        SignInModal signInModal = new SignInModal(driver);

        Assertions.assertEquals(
                "Welcome back!",
                signInModal.getModalTitleText(),
                "Sign in modal is not displayed"
        );

        homePage = signInModal.close(HomePage.class);

        homePage.getHeader().clickUbsCourier();

        Assertions.assertTrue(
                driver.getCurrentUrl().contains("#/ubs"),
                "UBS courier page was not opened"
        );

    }
}
