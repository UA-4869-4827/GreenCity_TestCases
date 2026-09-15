package com.greencity.ui;

import com.greencity.ui.modal.ForgotPasswordModal;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignInTest extends BaseTestRunner {

    // TC-P1-SIN-01 – Sign in chrome, disabled submit, show/hide, and sibling links
    // #48//

    @Test
    void signInModalShouldDisplayRequiredElements() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        assertEquals("Welcome back!", signInModal.getModalTitleText());

        assertTrue(signInModal.isEmailDisplayed());
        assertTrue(signInModal.isPasswordDisplayed());
        assertTrue(signInModal.isForgotPasswordDisplayed());
        assertTrue(signInModal.isGoogleButtonDisplayed());
        assertTrue(signInModal.isSignUpDisplayed());
        assertTrue(signInModal.isCloseButtonDisplayed());
    }

    @Test
    void signInButtonShouldBeDisabledWhenFieldsAreEmpty() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        assertFalse(signInModal.isSignInButtonEnabled());
    }

    @Test
    void passwordShowHideShouldWork() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        signInModal.enterPassword("TestPassword123");

        assertEquals("password", signInModal.getPasswordFieldType());

        signInModal.showPassword();

        assertEquals("text", signInModal.getPasswordFieldType());

        signInModal.showPassword();

        assertEquals("password", signInModal.getPasswordFieldType());
    }

    @Test
    void signUpLinkShouldOpenSignUpModal() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        SignUpModal signUpModal = signInModal.openSignUp();

        assertEquals("Hello!", signUpModal.getModalTitleText());
    }

    @Test
    void forgotPasswordLinkShouldOpenRestorePasswordModal() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        ForgotPasswordModal forgotPasswordModal = signInModal.openForgotPassword();

        assertEquals("Trouble singing in?", forgotPasswordModal.getModalTitleText());
    }

    @Test
    void closeButtonShouldCloseSignInModal() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        HomePage homePageAfterClose = signInModal.close(HomePage.class);

        assertTrue(homePageAfterClose.getHeader().isSignInDisplayed());
    }

    // TC-P1-SIN-02 – Sign in field errors and bad credentials #49//

    @Test
    void emptyEmailShouldShowRequiredError() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        signInModal.enterEmail("");
        signInModal.enterPassword("TestPassword123");

        assertEquals(UiMessage.SIGN_IN_EMAIL_REQUIRED.text(),
                signInModal.getEmailErrorText());
    }

    @Test
    void emptyPasswordShouldShowRequiredError() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        signInModal.enterEmail("test@example.com");
        signInModal.enterPassword("");
        signInModal.clickEmailField();

        assertEquals("This field is required",
                signInModal.getPasswordErrorText());
    }

    @Test
    void shortPasswordShouldShowValidationMessage() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();
        signInModal.enterEmail("test@example.com");
        signInModal.enterPassword("Test12");
        signInModal.clickEmailField();

        assertTrue(signInModal.getPasswordErrorText().contains(UiMessage.SIGN_IN_PASSWORD_VALIDATION.text()),
                "Expected password validation message");
    }

    @Test
    void wrongCredentialsShouldShowErrorAndKeepModalOpen() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        signInModal.unsuccessfulSignIn(
                "wrong@example.com",
                "WrongPassword123");

        assertEquals(UiMessage.SIGN_IN_INVALID_CREDENTIALS.text(),
                signInModal.getPasswordErrorText());

        assertTrue(signInModal.isCloseButtonDisplayed());
    }

    // TC-P1-SIN-03 – Valid credentials close the modal and authenticate the header
    // #50//
    @Test
    void registeredUserShouldSignInSuccessfully() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();

        HomePage homePageAfterSignIn = signInModal.signIn(
                testValueProvider.getUserEmail(),
                testValueProvider.getUserPassword(),
                HomePage.class);

        assertTrue(homePageAfterSignIn.getHeader().isLoggedIn());
        assertFalse(homePageAfterSignIn.getHeader().isSignUpDisplayed());
    }
}
