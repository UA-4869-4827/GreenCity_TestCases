package com.greencity.ui;

import com.greencity.ui.modal.ForgotPasswordModal;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SignInTest extends BaseTestRunner {

    //TC-P1-SIN-01 – Sign in chrome, disabled submit, show/hide, and sibling links #48//

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


}