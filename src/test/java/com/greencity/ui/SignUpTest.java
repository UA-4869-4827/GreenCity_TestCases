package com.greencity.ui;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("US-20: Sign up with credentials and validation")
public class SignUpTest extends BaseTestRunner {

    private static final String VALID_EMAIL = "autotest@gmail.com";
    private static final String VALID_USER_NAME = "TestUser";
    private static final String VALID_PASSWORD = "Qwerty_1!";

    private SignUpModal openSignUpModal() {
        return homePage.getHeader().clickSignUp();
    }

    @Test
    @DisplayName("TC-01: modal elements, disabled submit, show/hide, switch to Sign in")
    void signUpModalChromeAndSwitchToSignIn() {
        SignUpModal modal = openSignUpModal();

        assertAll("AC-1: all elements are present",
                () -> assertTrue(modal.isEmailInputDisplayed(), "Email input"),
                () -> assertTrue(modal.isUserNameInputDisplayed(), "User name input"),
                () -> assertTrue(modal.isPasswordInputDisplayed(), "Password input"),
                () -> assertTrue(modal.isRepeatPasswordInputDisplayed(), "Confirm password input"),
                () -> assertTrue(modal.isSignUpButtonDisplayed(), "Sign up button"),
                () -> assertTrue(modal.isGoogleButtonDisplayed(), "Google button"),
                () -> assertTrue(modal.isSignInLinkDisplayed(), "Sign in link")
        );

        assertFalse(modal.isSignUpButtonEnabled(), "AC-2: disabled on empty form");

        modal.enterEmail(VALID_EMAIL)
                .enterUserName(VALID_USER_NAME)
                .enterPassword(VALID_PASSWORD);
        assertFalse(modal.isSignUpButtonEnabled(), "AC-2: disabled without confirm password");

        modal.enterRepeatPassword(VALID_PASSWORD);
        assertTrue(modal.isSignUpButtonEnabled(), "AC-2: enabled on valid form");

        assertAll("AC-10: both fields masked",
                () -> assertEquals("password", modal.getPasswordFieldType()),
                () -> assertEquals("password", modal.getRepeatPasswordFieldType())
        );

        modal.showPassword();
        assertAll("AC-10: only first field revealed",
                () -> assertEquals("text", modal.getPasswordFieldType()),
                () -> assertEquals("password", modal.getRepeatPasswordFieldType())
        );

        modal.showRepeatPassword();
        assertEquals("text", modal.getRepeatPasswordFieldType(), "AC-10: second field revealed");

        SignInModal signInModal = modal.openSignIn();
        assertEquals("Welcome back!", signInModal.getModalTitleText(), "AC-11: Sign in modal opened");
    }

    @ParameterizedTest(name = "{0}")
    @CsvSource(delimiter = '|', value = {
            "AC-3 empty email          | ''                | TestUser  | Qwerty_1! | Qwerty_1!      | EMAIL   | Email is required.",
            "AC-4 email without @      | plaintext         | TestUser  | Qwerty_1! | Qwerty_1!      | EMAIL   | Please check if the email is written correctly",
            "AC-4 email without name   | @mail.com         | TestUser  | Qwerty_1! | Qwerty_1!      | EMAIL   | Please check if the email is written correctly",
            "AC-5 empty user name      | autotest@mail.com | ''        | Qwerty_1! | Qwerty_1!      | NAME    | User name is required.",
            "AC-6 name starts with dot | autotest@mail.com | .name     | Qwerty_1! | Qwerty_1!      | NAME    | The user name must be 1-30 characters long",
            "AC-6 name with two dots   | autotest@mail.com | na..me    | Qwerty_1! | Qwerty_1!      | NAME    | The user name must be 1-30 characters long",
            "AC-7 password too short   | autotest@mail.com | TestUser  | Qw_1!     | Qw_1!          | PASS    | Password have from 8 to 20 characters",
            "AC-7 password no digit    | autotest@mail.com | TestUser  | Qwertyui! | Qwertyui!      | PASS    | Password have from 8 to 20 characters",
            "AC-7 password no special  | autotest@mail.com | TestUser  | Qwerty123 | Qwerty123      | PASS    | Password have from 8 to 20 characters",
            "AC-8 empty confirm        | autotest@mail.com | TestUser  | Qwerty_1! | ''             | CONFIRM | This field is required",
            "AC-9 passwords mismatch   | autotest@mail.com | TestUser  | Qwerty_1! | Qwerty_1!extra | CONFIRM | Passwords do not match"
    })
    @DisplayName("TC-02: validation messages for invalid fields")
    void invalidFieldShowsValidationError(String caseName, String email, String userName,
                                          String password, String repeatPassword,
                                          String field, String expectedError) {
        SignUpModal modal = openSignUpModal()
                .enterEmail(email)
                .enterUserName(userName)
                .enterPassword(password)
                .enterRepeatPassword(repeatPassword)
                .enterPassword(password);

        String actualError = switch (field) {
            case "EMAIL" -> modal.getEmailErrorText();
            case "NAME" -> modal.getFirstNameErrorText();
            case "PASS" -> modal.getPasswordNotValidMessage();
            case "CONFIRM" -> modal.getConfirmErrorText();
            default -> throw new IllegalArgumentException("Unknown field: " + field);
        };

        assertAll(
                () -> assertFalse(actualError.isBlank(), "Validation message is not shown"),
                () -> assertFalse(modal.isSignUpButtonEnabled(), "Submit must stay disabled"),
                () -> System.out.println("[" + caseName + "] actual: " + actualError)
        );
    }

    @Test
    @DisplayName("AC-12: successful sign up closes the modal")
    void successfulSignUpClosesModal() {
        SignUpModal modal = openSignUpModal();

        modal.signUp("autotest" + System.currentTimeMillis() + "@gmail.com",
                VALID_USER_NAME, VALID_PASSWORD, HomePage.class);

        assertTrue(modal.isModalClosed(), "Sign up modal should be closed after successful sign up");
    }



}