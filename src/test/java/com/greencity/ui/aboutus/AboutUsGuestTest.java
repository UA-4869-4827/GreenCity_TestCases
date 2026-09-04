package com.greencity.ui.aboutus;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

// TC-P0-ABT-01 - About us content and guest CTAs
public class AboutUsGuestTest extends BaseTestRunner {

    @Test
    void aboutUsHeadingsAreVisible() {
        AboutUsPage aboutUsPage = homePage.getHeader().openAboutUs();

        assertTrue(aboutUsPage.isAboutUsHeadingDisplayed(), "'About Us' heading is not displayed.");
        assertTrue(aboutUsPage.isOurVisionHeadingDisplayed(), "'Our vision' heading is not displayed.");
    }

    @Test
    void guestFormHabitFromAboutUsHeadingStaysOnAboutUs() {
        AboutUsPage aboutUsPage = homePage.getHeader().openAboutUs();

        AboutUsPage result = aboutUsPage.formHabitFromAboutUsHeadingAsGuest();

        assertTrue(result.isOpened(), "Guest should stay on About us page.");
    }

    @Test
    void guestFormHabitFromOurVisionHeadingStaysOnAboutUs() {
        AboutUsPage aboutUsPage = homePage.getHeader().openAboutUs();

        AboutUsPage result = aboutUsPage.formHabitFromOurVisionHeadingAsGuest();

        assertTrue(result.isOpened(), "Guest should stay on About us page.");
    }

    @Test
    void findEcoPlacesOpensPlaces() {
        AboutUsPage aboutUsPage = homePage.getHeader().openAboutUs();

        assertTrue(aboutUsPage.findEcoPlaces().isOpened(), "Places page did not open.");
    }

    @Test
    void getInspiredOpensEcoNews() {
        AboutUsPage aboutUsPage = homePage.getHeader().openAboutUs();

        assertTrue(aboutUsPage.getInspired().isOpened(), "Eco news page did not open.");
    }

    @Test
    void guestFindPeopleOpensSignIn() {
        AboutUsPage aboutUsPage = homePage.getHeader().openAboutUs();

        SignInModal signIn = aboutUsPage.findPeopleFromEcoProductsHeadingAsGuest();

        assertTrue(signIn.getModalTitleText().contains("Welcome back"), "Sign in modal did not open.");
    }
}
