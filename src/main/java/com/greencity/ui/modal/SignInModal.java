package com.greencity.ui.modal;

import com.greencity.ui.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInModal extends BaseModal<SignInModal> {

    @FindBy(css = "app-sign-in h1")
    private WebElement modalTitle;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "img.image-show-hide-password")
    private WebElement showHidePasswordButton;

    @FindBy(css = "form.sign-in-form button[type='submit']")
    private WebElement signInButton;

    @FindBy(css = "a.forgot-password")
    private WebElement forgotPasswordLink;

    @FindBy(css = "div.missing-account a.green-link")
    private WebElement signUpLink;

    @FindBy(id = "email-err-msg")
    private WebElement emailErrorMessage;

    // Validation error shown under password field: id="pass-err-msg" > .margining
    @FindBy(css = "#pass-err-msg .margining")
    private WebElement passwordFieldErrorMessage;

    // Server-side error after submit (e.g. "Bad email or password")
    @FindBy(css = "div.alert-general-error")
    private WebElement passwordGeneralErrorMessage;

    public SignInModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected SignInModal self() {
        return this;
    }

    @Step("Enter password")
    public SignInModal enterPassword(String password) {
        return enterInto(passwordInput, password);
    }

    @Step("Click 'Sign in' button")
    public SignInModal clickSignInButton() {
        return clickOn(signInButton);
    }

    public SignInModal waitUntilSignInCompleted() {
        waitUntilClosed();
        return this;
    }

    @Step("Toggle password visibility")
    public SignInModal showPassword() {
        return clickOn(showHidePasswordButton);
    }

    public boolean isSignInButtonEnabled() {
        waitUntilElementVisible(signInButton);
        return signInButton.isEnabled();
    }

    public String getModalTitleText() {
        waitUntilElementVisible(modalTitle);
        wait.until(d -> !modalTitle.getText().trim().isEmpty());
        return modalTitle.getText().trim();
    }

    public String getEmailErrorText() {
        waitUntilElementVisible(emailErrorMessage);
        wait.until(d -> !emailErrorMessage.getText().trim().isEmpty());
        return emailErrorMessage.getText().trim();
    }

    /**
     * Returns the password error text.
     * Checks field-level validation error first (id="pass-err-msg"),
     * then falls back to the general server-side error (div.alert-general-error).
     */
    public String getPasswordErrorText() {
        wait.until(d -> {
            try {
                if (passwordFieldErrorMessage.isDisplayed()
                        && !passwordFieldErrorMessage.getText().trim().isEmpty()) {
                    return true;
                }
            } catch (Exception ignored) {
                // element not present yet
            }
            try {
                if (passwordGeneralErrorMessage.isDisplayed()
                        && !passwordGeneralErrorMessage.getText().trim().isEmpty()) {
                    return true;
                }
            } catch (Exception ignored) {
                // element not present yet
            }
            return false;
        });

        try {
            if (passwordFieldErrorMessage.isDisplayed()
                    && !passwordFieldErrorMessage.getText().trim().isEmpty()) {
                return passwordFieldErrorMessage.getText().trim();
            }
        } catch (Exception ignored) {
            // fall through to general error
        }

        return passwordGeneralErrorMessage.getText().trim();
    }

    public String getPasswordFieldType() {
        return getElementAttribute(passwordInput, "type");
    }

    @Step("Sign in with email {email}")
    public <P extends BasePage> P signIn(String email, String password, Class<P> pageClass) {
        fillCredentials(email, password);
        clickSignInButton();
        waitUntilClosed();
        return openPage(pageClass);
    }

    @Step("Unsuccessful sign in with email {email}")
    public SignInModal unsuccessfulSignIn(String email, String password) {
        fillCredentials(email, password);
        clickSignInButton();
        return this;
    }

    @Step("Open 'Sign up' modal")
    public SignUpModal openSignUp() {
        clickElementWithJs(signUpLink);
        return new SignUpModal(driver);
    }

    @Step("Open 'Forgot password' modal")
    public ForgotPasswordModal openForgotPassword() {
        clickElementWithJs(forgotPasswordLink);
        return new ForgotPasswordModal(driver);
    }

    private void fillCredentials(String email, String password) {
        enterEmail(email);
        enterPassword(password);
    }

    public boolean isEmailDisplayed() {
        return isElementDisplayed(emailInput);
    }

    public boolean isPasswordDisplayed() {
        return isElementDisplayed(passwordInput);
    }

    public boolean isForgotPasswordDisplayed() {
        return isElementDisplayed(forgotPasswordLink);
    }

    public boolean isSignUpDisplayed() {
        return isElementDisplayed(signUpLink);
    }

    public boolean isCloseButtonDisplayed() {
        return isElementDisplayed(closeButton);
    }

    public SignInModal clickEmailField() {
        clickElement(emailInput);
        return this;
    }

    public SignInModal clickPasswordField() {
        clickElement(passwordInput);
        return this;
    }
}
