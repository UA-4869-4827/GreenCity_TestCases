package com.greencity.ui.page.signup;

import com.greencity.ui.Base;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.signin.SignInPage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Getter
public class SignUpPage extends Base {

    @FindBy(css = "app-sign-up h1")
    private WebElement modalTitle;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "firstName")
    private WebElement userNameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "repeatPassword")
    private WebElement repeatPasswordInput;

    @FindBy(css = "#password + .show-password-img")
    private WebElement showHidePasswordButton;

    @FindBy(css = "#repeatPassword + .show-password-img")
    private WebElement showHideRepeatPasswordButton;

    @FindBy(css = "form.form-content-container button[type='submit']")
    private WebElement signUpButton;

    @FindBy(css = "app-google-btn button.google-sign-in")
    private WebElement googleSignUpButton;

    @FindBy(css = "div.exist-account a.green-link")
    private WebElement signInLink;

    @FindBy(css = "a.close-modal-window")
    private WebElement closeButton;

    @FindBy(id = "email-err-msg")
    private WebElement emailErrorMessage;

    @FindBy(id = "firstname-err-msg")
    private WebElement firstNameErrorMessage;

    @FindBy(id = "password-err-msg")
    private WebElement passwordErrorMessage;

    @FindBy(id = "confirm-err-msg")
    private WebElement confirmErrorMessage;

    @FindBy(css = "p.password-not-valid")
    private WebElement passwordNotValidMessage;


    public SignUpPage(WebDriver driver) {
        super(driver);
    }


//Functional

    @Step("Enter email: {email}")
    public SignUpPage enterEmail(String email) {
        waitUntilElementVisible(emailInput);
        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Enter userName: {userName}")
    public SignUpPage enterUserName(String userName) {
        waitUntilElementVisible(userNameInput);
        userNameInput.click();
        userNameInput.clear();
        userNameInput.sendKeys(userName);
        return this;
    }

    @Step("Enter password")
    public SignUpPage enterPassword(String password) {
        waitUntilElementVisible(passwordInput);
        passwordInput.click();
        passwordInput.clear();
        passwordInput.sendKeys(password);
        return this;
    }

    @Step("Enter repeatPassword")
    public SignUpPage enterRepeatPassword(String repeatPassword) {
        waitUntilElementVisible(repeatPasswordInput);
        repeatPasswordInput.click();
        repeatPasswordInput.clear();
        repeatPasswordInput.sendKeys(repeatPassword);
        return this;
    }

    @Step("Click 'Sign up' button")
    public void clickSignUpButton() {
        waitUntilElementClickable(signUpButton);
        signUpButton.click();
    }

    @Step("Show/hide password")
    public SignUpPage showPassword() {
        waitUntilElementClickable(showHidePasswordButton);
        showHidePasswordButton.click();
        return this;
    }

    @Step("Show/hide repeat password")
    public SignUpPage showRepeatPassword() {
        waitUntilElementClickable(showHideRepeatPasswordButton);
        showHideRepeatPasswordButton.click();
        return this;
    }

    // State

    public boolean isSignUpButtonEnabled() {
        return signUpButton.isEnabled();
    }

    public String getModalTitleText() {
        return modalTitle.getText();
    }

    public String getEmailErrorText() {
        waitUntilElementVisible(emailErrorMessage);
        return emailErrorMessage.getText();
    }

    public String getFirstNameErrorText() {
        waitUntilElementVisible(firstNameErrorMessage);
        return firstNameErrorMessage.getText();
    }

    public String getPasswordErrorText() {
        waitUntilElementVisible(passwordErrorMessage);
        return passwordErrorMessage.getText();
    }

    public String getConfirmErrorText() {
        waitUntilElementVisible(confirmErrorMessage);
        return confirmErrorMessage.getText();
    }

    public String getPasswordNotValidMessage() {
        waitUntilElementVisible(passwordNotValidMessage);
        return passwordNotValidMessage.getText();
    }

    public String getPasswordFieldType() {
        return passwordInput.getAttribute("type");
    }

    public String getRepeatPasswordFieldType() {
        return repeatPasswordInput.getAttribute("type");
    }

    //Business Logic

    @Step("Sign up with email {email}")
    public HomePage signUp(String email, String userName, String password) {
        enterEmail(email);
        enterUserName(userName);
        enterPassword(password);
        enterRepeatPassword(password);
        clickSignUpButton();
        return new HomePage(driver);
    }

    @Step("Unsuccessful sign up with email {email}")
    public SignUpPage unsuccessfulSignUp(String email, String userName, String password, String repeatPassword) {
        enterEmail(email);
        enterUserName(userName);
        enterPassword(password);
        enterRepeatPassword(repeatPassword);
        clickSignUpButton();
        return this;
    }

    @Step("Open 'Sign in' modal")
    public SignInPage openSignIn() {
        waitUntilElementClickable(signInLink);
        signInLink.click();
        return new SignInPage(driver);
    }

    @Step("Close sign up modal")
    public HomePage close() {
        waitUntilElementClickable(closeButton);
        closeButton.click();
        return new HomePage(driver);
    }

    @Step("Sign up with Google")
    public void signUpWithGoogle() {
        waitUntilElementClickable(googleSignUpButton);
        googleSignUpButton.click();
    }

}