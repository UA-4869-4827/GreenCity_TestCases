package com.greencity.ui.pages;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.ubscourier.UbsCourierPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class GuestHomePageTest extends BaseTestRunner {

//    TC-P0-HOME-01 – Hero copy and Start forming a habit gate the guest #42

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
        assertEquals(UiMessage.HOMEPAGE_HABIT_BUTTON.text(), homePage.getHeroStartHabitButtonText(), "Hero start habit button text is incorrect");
    }

    @Test
    void guestShouldBeAbleToOpenSignInModalByClickingHeroHabitButton() {
        SignInModal signInModal = homePage.clickHeroStartHabitButtonAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }

//        TC-P1-STAT-01 – Stats block is visible and gates the guest #43

    @Test
    void guestShouldSeeBagsQuestion() {
        assertTrue(homePage.isBagsQuestionTextVisible(), "Bags question is not displayed");
    }

    @Test
    void guestShouldSeeBagsStartHabitButton() {
        assertTrue(homePage.isBagsStartHabitButtonVisible(), "Bags start habit button is not displayed.");
        assertEquals(UiMessage.HOMEPAGE_HABIT_BUTTON.text(), homePage.getBagsStartHabitButtonText(), "Bags start habit button text is incorrect");
    }

    @Test
    void guestShouldBeAbleToOpenSignInModalByClickingBagsStartHabitButton() {
        SignInModal signInModal = homePage.clickBagsStartHabitButtonAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }

    @Test
    void guestShouldSeeCupsQuestion() {
        assertTrue(homePage.isCupsQuestionTextVisible(), "Cups question is not displayed");
    }

    @Test
    void guestShouldSeeCupsStartHabitButton() {
        assertTrue(homePage.isCupsStartHabitButtonVisible(), "Cups start habit button is not displayed.");
        assertEquals(UiMessage.HOMEPAGE_HABIT_BUTTON.text(), homePage.getCupsStartHabitButtonText(), "Cups start habit button text is incorrect");
    }

    @Test
    void guestShouldBeAbleToOpenSignInModalByClickingCupsStartHabitButton() {
        SignInModal signInModal = homePage.clickCupsStartHabitButtonAsGuest();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }

//    TC-P1-STAT-02 – Community heading, guest zeros, and eco-bag links open UBS #44

    @Test
    void guestShouldSeeCommunityHeading() {
        assertTrue(homePage.isStatsHeadingVisible(), "Community heading is not displayed");
        assertEquals(UiMessage.HOMEPAGE_COMMUNITY_HEADING.text(), homePage.getStatsHeadingText(), "Community heading is incorrect");
    }

    @Test
    void guestShouldSeeBagsCounter() {
        assertTrue(homePage.isBagsCounterLabelVisible(), "Bags counter label is not displayed");
        assertEquals(UiMessage.HOMEPAGE_BAGS_COUNTER.text(), homePage.getBagsCounterLabelText(), "Bags counter label text is incorrect");
    }

    @Test
    void guestShouldSeeCupsCounter() {
        assertTrue(homePage.isCupsCounterLabelVisible(), "Cups counter label is not displayed");
        assertEquals(UiMessage.HOMEPAGE_CUPS_COUNTER.text(), homePage.getCupsCounterLabelText(), "Cups counter label text is incorrect");
    }

    @Test
    void guestShouldBeAbleToOpenUbsCourierByClickingBuyEcoBags() {
        UbsCourierPage ubsCourierPage = homePage.openEcoBagsPlaces();
        assertTrue(ubsCourierPage.isOpened(), "UBS Courier page is not opened after clicking Buy Eco Bags");
    }

    @Test
    void guestShouldBeAbleToOpenUbsCourierByClickingCupDiscountPlaces() {
        UbsCourierPage ubsCourierPage = homePage.openCupDiscountPlaces();
        assertTrue(ubsCourierPage.isOpened(), "UBS Courier page is not opened after clicking Cup Discount Places");
    }

//    TC-P1-NSL-01 – Newsletter chrome and invalid email #45

    @Test
    void guestShouldSeeNewsletterHeading() {
        assertTrue(homePage.isNewsletterTitleVisible(), "Newsletter heading is not displayed");
        assertEquals(UiMessage.HOMEPAGE_NEWSLETTER_HEADING.text(), homePage.getNewsletterTitle(), "Newsletter heading text is incorrect");
    }

    @Test
    void guestShouldSeeNewsletterDescription() {
        assertTrue(homePage.isNewsletterDescriptionVisible(), "Newsletter description is not displayed");
    }

    @Test
    void guestShouldSeeNewsletterQrCode() {
        assertTrue(homePage.isQrCodeDisplayed(), "Newsletter QR code is not displayed");
    }

    @Test
    void guestShouldSeeNewsletterPlaceholder() {
        assertEquals(UiMessage.HOMEPAGE_NEWSLETTER_PLACEHOLDER.text(), homePage.getEmailPlaceholderText(), "Newsletter email placeholder text is incorrect");
    }

    @Test
    void guestShouldSeeSubscribeButton() {
        assertTrue(homePage.isSubscribeButtonVisible(), "Subscribe button is not displayed");
        assertEquals(UiMessage.HOMEPAGE_NEWSLETTER_SUBSCRIBE_BUTTON.text(), homePage.getSubscribeButtonText(), "Subscribe button text is incorrect");
    }

    @Test
    void guestShouldSeeValidationErrorEnteringEmptyEmail() {
        homePage.subscribe("");
        assertTrue(homePage.isEmailValidationErrorDisplayed(), "Email validation error is not displayed for an empty email");
        assertEquals(UiMessage.HOMEPAGE_NEWSLETTER_ERROR_VALIDATION.text(), homePage.getEmailValidationErrorText(), "Email validation error text is incorrect for an empty email");
        assertFalse(homePage.isSuccessSubscriptionMessageVisible(), "Success snackbar is displayed after subscribing with an empty email");
    }

    @Test
    void guestShouldSeeValidationErrorEnteringInvalidEmail() {
        homePage.subscribe("invalid-email");
        assertTrue(homePage.isEmailValidationErrorDisplayed(), "Email validation error is not displayed for an invalid email");
        assertEquals(UiMessage.HOMEPAGE_NEWSLETTER_ERROR_VALIDATION.text(), homePage.getEmailValidationErrorText(), "Email validation error text is incorrect for an invalid email");
        assertFalse(homePage.isSuccessSubscriptionMessageVisible(), "Success snackbar is displayed after subscribing with an invalid email");
    }

//    TC-P1-NSL-02 – Unique valid email shows a success snackbar #46

    @Test
    void guestShouldSeeSuccessSnackbarAfterSubscribingWithUniqueEmail() {
        String uniqueEmail = "greencity.sub." + UUID.randomUUID() + "@example.com";
        homePage.subscribe(uniqueEmail);
        assertEquals(UiMessage.HOMEPAGE_NEWSLETTER_SUBSCRIBE_BUTTON.text(), homePage.getSubscribeButtonText(), "Subscribe button text is incorrect");
        assertTrue(homePage.isSuccessSubscriptionMessageVisible(), "Success snackbar is not displayed after subscribing with a unique valid email");
    }

    @Test
    void guestShouldSeeErrorSnackbarAfterSubscribingWithAlreadySubscribedEmail() {
        String userEmail = testValueProvider.getUserEmail();
        homePage.subscribe(userEmail);
        assertTrue(homePage.isErrorSubscriptionMessageVisible(), "Error snackbar is not displayed after subscribing with an already subscribed email");
    }
}
