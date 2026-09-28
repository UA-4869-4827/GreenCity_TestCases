package com.greencity.ui.pages;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.ForgotPasswordModal;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuestForgotPasswordModalTest extends BaseTestRunner {

    private ForgotPasswordModal forgotPasswordModal;

    @BeforeEach
    void beforeEachForgotPasswordModal() {
        SignInModal signInModal = homePage.getHeader().clickSignIn();
        forgotPasswordModal = signInModal.openForgotPassword();
    }

    //    "TC-P1-FGT-01 - Forgot password modal displays required elements"

    @Test
    void forgotPasswordModalShouldDisplayTitle() {
        assertEquals(UiMessage.FORGOT_PASSWORD_TITLE.text(), forgotPasswordModal.getModalTitleText(), "Forgot password title is incorrect");
    }

    @Test
    void forgotPasswordModalShouldDisplaySubTitle() {
        assertEquals(UiMessage.FORGOT_PASSWORD_SUBTITLE.text(), forgotPasswordModal.getModalSubtitleText(), "Forgot password subtitle is incorrect");
    }

    @Test
    void forgotPasswordModalShouldDisplayEmailField() {
        assertTrue(forgotPasswordModal.isEmailFieldDisplayed(), "Email field is not displayed");
    }

    @Test
    void forgotPasswordModalShouldDisplaySendLinkButtonDisabled() {
        assertTrue(forgotPasswordModal.isSendLinkButtonDisplayed(), "Submit a login link button is not displayed");
        assertFalse(forgotPasswordModal.isSendLinkButtonEnabled(), "Submit a login link button should be disabled for an empty email");
        assertEquals(UiMessage.FORGOT_PASSWORD_SEND_LINK_BUTTON_TEXT.text(), forgotPasswordModal.getSendLinkButtonText(), "Submit a login link button text is incorrect");
    }

    @Test
    void forgotPasswordModalShouldDisplayGoogleSignIn() {
        assertTrue(forgotPasswordModal.isGoogleSignInDisplayed(), "Sign in with Google is not displayed");
        assertEquals(UiMessage.FORGOT_PASSWORD_SIGN_IN_WITH_GOOGLE_TEXT.text(), forgotPasswordModal.getGoogleSignInText(), "Sign in with Google text is incorrect");
    }

    @Test
    void forgotPasswordModalShouldDisplayBackToSignIn() {
        assertTrue(forgotPasswordModal.isBackToSignInVisible(), "Back to Sign in link is not displayed");
        assertEquals(UiMessage.FORGOT_PASSWORD_BACK_TO_SIGN_IN_TEXT.text(), forgotPasswordModal.getBackToSignInText(), "Back to Sign in link text is incorrect");
    }

    @Test
    void forgotPasswordModalShouldReturnToSignInModal() {
        SignInModal signInModal = forgotPasswordModal.backToSignIn();
        assertEquals(UiMessage.SIGN_IN_MODAL_WELCOME_TEXT.text(), signInModal.getModalTitleText(), "Sign In modal title is incorrect");
    }

    @Test
    void forgotPasswordModalShouldDisplayErrorMessageWhileEnteringInvalidEmail() {
        forgotPasswordModal.enterEmail("invalidEmail");
        assertTrue(forgotPasswordModal.isEmailErrorMessageVisible(), "Invalid email error message is not displayed");
        assertEquals(UiMessage.FORGOT_PASSWORD_INVALID_EMAIL_ERROR_MESSAGE.text(), forgotPasswordModal.getEmailErrorMessageText(), "Invalid email error message is incorrect");
        assertFalse(forgotPasswordModal.isSendLinkButtonEnabled(), "Submit a login link button should be disabled for an invalid email");
    }

    @Test
    void forgotPasswordModalShouldDisplayErrorMessageWhileEnteringEmptyEmail() {
        forgotPasswordModal.enterEmail("");
        assertTrue(forgotPasswordModal.isEmailErrorMessageVisible(), "Empty email error message is not displayed");
        assertEquals(UiMessage.FORGOT_PASSWORD_EMPTY_EMAIL_ERROR_MESSAGE.text(), forgotPasswordModal.getEmailErrorMessageText(), "Empty email error message is incorrect");
        assertFalse(forgotPasswordModal.isSendLinkButtonEnabled(), "Submit a login link button should be disabled for an empty email");
    }

//    TC-P1-FGT-02 – Unregistered email is rejected#54

    @Test
    void forgotPasswordModalShouldDisplayErrorMessageWhileEnteringUnregisteredEmail() {
        String email = "validUnregisteredEmail@gmail.com";
        forgotPasswordModal.enterEmail(email);
        assertTrue(forgotPasswordModal.isSendLinkButtonEnabled(), "Submit a login link button should be enabled for a valid email");
        forgotPasswordModal.clickSendLinkButton();
        assertTrue(forgotPasswordModal.isEmailErrorMessageVisible(), "Unregistered email error message is not displayed");
        assertEquals(UiMessage.FORGOT_PASSWORD_UNREGISTERED_EMAIL_ERROR_MESSAGE.text() + " " + email, forgotPasswordModal.getEmailErrorMessageText(), "Unregistered email error message is incorrect");
    }
}