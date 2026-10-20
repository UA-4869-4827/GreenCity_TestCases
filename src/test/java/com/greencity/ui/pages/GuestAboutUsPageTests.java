package com.greencity.ui.pages;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestAboutUsPageTests extends BaseTestRunner {

    private AboutUsPage aboutUsPage;

    @BeforeEach
    void beforeAboutUsPage() {
        aboutUsPage = homePage.getHeader().openAboutUs();
    }

//    TC-P0-ABT-01 – About us content and guest CTAs#47

    @Test
    void guestShouldSeeAboutUsPageTitles() {
        assertTrue(aboutUsPage.isAboutUsSectionTitleVisible(), "About Us section title is not visible");
        assertEquals(UiMessage.ABOUT_US_HEADER.text(), aboutUsPage.getAboutUsSectionTitle(), "About Us section title is incorrect");
        assertTrue(aboutUsPage.isOurVisionSectionTitleVisible(), "Our Vision section title is not visible");
        assertEquals(UiMessage.ABOUT_US_VISION_HEADER.text(), aboutUsPage.getOurVisionSectionTitle(), "Our Vision section title is incorrect");
        assertTrue(aboutUsPage.isEasierWithUsSectionTitleVisible(), "Easier With Us section title is not visible");
        assertEquals(UiMessage.ABOUT_US_EASIER_HEADER.text(), aboutUsPage.getEasierWithUsSectionTitle(), "Easier With Us section title is incorrect");
    }

    @Test
    void guestShouldSeeSignInModalByClickingOnFindEcoPlaces() {
        SignInModal signInModal = aboutUsPage.findEcoPlacesAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }

    @Test
    void guestShouldSeeSignInModalByClickingOnFindPeople() {
        SignInModal signInModal = aboutUsPage.findPeopleFromEcoProductsHeadingAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }

    @Test
    void guestShouldSeeEcoNewsPageByClickingOnGetInspired() {
        EcoNewsPage ecoNewsPage = aboutUsPage.getInspired();
        assertEquals(UiMessage.NEWS_PAGE_HEADER.text(), ecoNewsPage.getPageHeadingText(), "Eco News page heading is incorrect");
    }

    @Test
    void guestShouldRemainOnAboutUsPageAfterClickingFormHabit() {
        aboutUsPage.formHabitFromAboutUsHeadingAsGuest();
        assertTrue(aboutUsPage.isAboutUsSectionTitleVisible(), "About Us section title is not visible after clicking Form Habit");
        aboutUsPage.formHabitFromOurVisionHeadingAsGuest();
        assertTrue(aboutUsPage.isOurVisionSectionTitleVisible(), "Our Vision section title is not visible after clicking Form Habit"
        );
    }
}
