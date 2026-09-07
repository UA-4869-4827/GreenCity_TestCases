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
        assertEquals(UiMessage.HOMEPAGE_HERO_TITLE.text(), homePage.getHeroTitleText(), "Hero title is incorrect");
    }

    @Test
    void guestShouldSeeHeroBlockDescription() {
        assertTrue(homePage.isHeroDescriptionTextVisible(), "Hero description is not displayed.");
    }

    @Test
    void guestShouldSeeHeroPicture() {
        assertTrue(homePage.isHeroPictureVisible(), "Hero picture is not displayed");
    }

    @Test
    void guestShouldSeeHeroStartHabitButton() {
        assertTrue(homePage.isHeroStartHabitButtonVisible(), "Hero start habit button is not displayed.");
    }

    @Test
    void signInModalShouldBeOpenWhileClickOnMainHabitButton() {
        SignInModal signInModal = homePage.clickHeroStartHabitButtonAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }
}
