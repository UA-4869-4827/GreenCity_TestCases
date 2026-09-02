package com.greencity.ui.modal;

import com.greencity.ui.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpModal extends BaseModal<SignUpModal> {

    @FindBy(css = "app-sign-up h1")
    private WebElement modalTitle;

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

    @FindBy(css = "div.exist-account a.green-link")
    private WebElement signInLink;

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

    public SignUpModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected SignUpModal self() {
        return this;
    }

    @Step("Enter user name: {userName}")
    public SignUpModal enterUserName(String userName) {
        return enterInto(userNameInput, userName);
    }

    @Step("Enter password")
    public SignUpModal enterPassword(String password) {
        return enterInto(passwordInput, password);
    }

    @Step("Enter repeat password")
    public SignUpModal enterRepeatPassword(String repeatPassword) {
        return enterInto(repeatPasswordInput, repeatPassword);
    }

    @Step("Click 'Sign up' button")
    public SignUpModal clickSignUpButton() {
        return clickOn(signUpButton);
    }

    @Step("Show/hide password")
    public SignUpModal showPassword() {
        return clickOn(showHidePasswordButton);
    }

    @Step("Show/hide repeat password")
    public SignUpModal showRepeatPassword() {
        return clickOn(showHideRepeatPasswordButton);
    }

    public boolean isSignUpButtonEnabled() {
        return signUpButton.isEnabled();
    }

    public String getModalTitleText() {
        return getElementText(modalTitle);
    }

    public String getEmailErrorText() {
        return getElementText(emailErrorMessage);
    }

    public String getFirstNameErrorText() {
        return getElementText(firstNameErrorMessage);
    }

    public String getPasswordErrorText() {
        return getElementText(passwordErrorMessage);
    }

    public String getConfirmErrorText() {
        return getElementText(confirmErrorMessage);
    }

    public String getPasswordNotValidMessage() {
        return getElementText(passwordNotValidMessage);
    }

    public String getPasswordFieldType() {
        return getElementAttribute(passwordInput, "type");
    }

    public String getRepeatPasswordFieldType() {
        return getElementAttribute(repeatPasswordInput, "type");
    }

    @Step("Sign up with email {email}")
    public <P extends BasePage> P signUp(String email, String userName, String password, Class<P> pageClass) {
        fillForm(email, userName, password, password);
        clickSignUpButton();
        waitUntilClosed();
        return openPage(pageClass);
    }

    @Step("Unsuccessful sign up with email {email}")
    public SignUpModal unsuccessfulSignUp(String email, String userName, String password, String repeatPassword) {
        fillForm(email, userName, password, repeatPassword);
        clickSignUpButton();
        return this;
    }

    @Step("Open 'Sign in' modal")
    public SignInModal openSignIn() {
        clickElement(signInLink);
        return new SignInModal(driver);
    }

    private void fillForm(String email, String userName, String password, String repeatPassword) {
        enterEmail(email);
        enterUserName(userName);
        enterPassword(password);
        enterRepeatPassword(repeatPassword);
    }
}
