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

    @FindBy(className = "show-hide-btn")
    private WebElement showHidePasswordButton;

    @FindBy(css = "form.sign-in-form button[type='submit']")
    private WebElement signInButton;

    @FindBy(className = "forgot-password")
    private WebElement forgotPasswordLink;

    @FindBy(css = "div.missing-account a.green-link")
    private WebElement signUpLink;

    @FindBy(id = "email-err-msg")
    private WebElement emailErrorMessage;

    @FindBy(id = "pass-err-msg")
    private WebElement passwordErrorMessage;

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

    @Step("Toggle password visibility")
    public SignInModal showPassword() {
        return clickOn(showHidePasswordButton);
    }

    public boolean isSignInButtonEnabled() {
        waitUntilElementVisible(signInButton);
        return signInButton.isEnabled();
    }

    public String getModalTitleText() {
        return getElementText(modalTitle);
    }

    public String getEmailErrorText() {
        return getElementText(emailErrorMessage);
    }

    public String getPasswordErrorText() {
        return getElementText(passwordErrorMessage);
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
        clickElement(signUpLink);
        return new SignUpModal(driver);
    }

    @Step("Open 'Forgot password' modal")
    public ForgotPasswordModal openForgotPassword() {
        clickElement(forgotPasswordLink);
        return new ForgotPasswordModal(driver);
    }

    private void fillCredentials(String email, String password) {
        enterEmail(email);
        enterPassword(password);
    }
}
