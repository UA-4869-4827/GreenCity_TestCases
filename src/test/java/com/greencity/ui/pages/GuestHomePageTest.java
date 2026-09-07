package com.greencity.ui.pages;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestHomePageTest extends BaseTestRunner {

    @Test
    void guestShouldSeeHeroBlockTitle() {
        assertEquals(UiMessage.HOMEPAGE_HERO_TITLE.text(), homePage.getHeroTitleText());
    }

    @Test
    void guestShouldSeeHeroBlockDescription() {
        assertTrue(homePage.isHeroDescriptionTextVisible(), "Hero description is not displayed.");
    }

    @Test
    void guestShouldSeeHeroPicture() {
        assertTrue(homePage.isHeroPictureVisible());
    }

    @Test
    void guestShouldSeeHeroStartHabitButton() {
        assertTrue(homePage.isHeroStartHabitButtonVisible(), "Main start habit button is not displayed.");
    }

    @Test
    void signInModalShouldBeOpenWhileClickOnMainHabitButton() {
        SignInModal signInModal = homePage.clickHeroStartHabitButtonAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText());
    }
}
