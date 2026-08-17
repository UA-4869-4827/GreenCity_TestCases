package com.greencity.ui.page.signin;

import com.greencity.ui.Base;
import com.greencity.ui.page.forgotpassword.ForgotPasswordPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.signup.SignUpPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class SignInPage extends Base {

    @FindBy(css = "app-sign-in h1")
    private WebElement modalTitle;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(className = "show-hide-btn")
    private WebElement showHidePasswordButton;

    @FindBy(css = "form.sign-in-form button[type='submit']")
    private WebElement signInButton;

    @FindBy(css = "app-google-btn button.google-sign-in")
    private WebElement googleSignInButton;

    @FindBy(className = "forgot-password")
    private WebElement forgotPasswordLink;

    @FindBy(css = "div.missing-account a.green-link")
    private WebElement signUpLink;

    @FindBy(css = "a.close-modal-window")
    private WebElement closeButton;

    @FindBy(id = "email-err-msg")
    private WebElement emailErrorMessage;

    @FindBy(id = "pass-err-msg")
    private WebElement passwordErrorMessage;

    public SignInPage(WebDriver driver) {
        super(driver);
    }

    // Functional

    @Step("Enter email: {email}")
    public SignInPage enterEmail(String email) {
        waitUntilElementVisible(emailInput);
        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Enter password")
    public SignInPage enterPassword(String password) {
        waitUntilElementVisible(passwordInput);
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("Click 'Sign in' button")
    public void clickSignInButton() {
        waitUntilElementClickable(signInButton);
        signInButton.click();
    }

    @Step("Toggle password visibility")
    public SignInPage showPassword() {
        waitUntilElementClickable(showHidePasswordButton);
        showHidePasswordButton.click();
        return this;
    }

    // State

    public boolean isSignInButtonEnabled() {
        return signInButton.isEnabled();
    }

    public String getModalTitleText() {
        return modalTitle.getText();
    }

    public String getEmailErrorText() {
        return emailErrorMessage.getText();
    }

    public String getPasswordErrorText() {
        return passwordErrorMessage.getText();
    }

    public String getPasswordFieldType() {
        return passwordInput.getAttribute("type");
    }

    // Business Logic

    @Step("Sign in with email {email}")
    public HomePage signIn(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
        return new HomePage(driver);
    }

    @Step("Unsuccessful sign in with email {email}")
    public SignInPage unsuccessfulSignIn(String email, String password) {
        enterEmail(email);
        enterPassword(password);
        clickSignInButton();
        return this;
    }

    @Step("Open 'Sign up' modal")
    public SignUpPage openSignUp() {
        waitUntilElementClickable(signUpLink);
        signUpLink.click();
        return new SignUpPage(driver);
    }

    @Step("Open 'Forgot password' modal")
    public ForgotPasswordPage openForgotPassword() {
        waitUntilElementClickable(forgotPasswordLink);
        forgotPasswordLink.click();
        return new ForgotPasswordPage(driver);
    }

    @Step("Click 'Sign in with Google' button")
    public void signInWithGoogle() {
        waitUntilElementClickable(googleSignInButton);
        googleSignInButton.click();
    }

    @Step("Close sign in modal")
    public HomePage close() {
        waitUntilElementClickable(closeButton);
        closeButton.click();
        return new HomePage(driver);
    }
}