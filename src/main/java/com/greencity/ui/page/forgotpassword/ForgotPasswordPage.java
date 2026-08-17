package com.greencity.ui.page.forgotpassword;

import com.greencity.ui.Base;
import com.greencity.ui.page.homepage.HomePage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import com.greencity.ui.page.signin.SignInPage;

@Getter
public class ForgotPasswordPage extends Base {

    @FindBy(css = "app-restore-password h1")
    private WebElement modalTitle;

    @FindBy(css = "app-restore-password h2")
    private WebElement modalSubtitle;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(css = "form.restore-password-form button[type='submit']")
    private WebElement sendLinkButton;

    @FindBy(css = "app-google-btn button.google-sign-in")
    private WebElement googleSignInButton;

    @FindBy(css = "a.close-modal-window")
    private WebElement closeButton;

    @FindBy(css = "div.mentioned-password a.green-link")
    private WebElement backToSignInButton;

    @FindBy(css = "form.restore-password-form .validation-email-error")
    private WebElement emailErrorMessage;


    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    //Functional

    @Step("Enter email: {email}")
    public ForgotPasswordPage enterEmail(String email) {
        waitUntilElementVisible(emailInput);
        emailInput.click();
        emailInput.clear();
        emailInput.sendKeys(email);
        return this;
    }

    @Step("Click 'Send link' button")
    public void clickSendLinkButton() {
        waitUntilElementClickable(sendLinkButton);
        sendLinkButton.click();
    }

    //State

    public String getModalTitleText() {
        return modalTitle.getText();
    }

    public String getModalSubtitleText() {
        return modalSubtitle.getText();
    }

    public String getEmailErrorMessageText() {
        waitUntilElementVisible(emailErrorMessage);
        return emailErrorMessage.getText();
    }

    public boolean isSendLinkButtonEnabled() {
        return sendLinkButton.isEnabled();
    }


    //Business Logic

    @Step("Submit login link for email: {email}")
    public HomePage submitLoginLink(String email) {
        enterEmail(email);
        clickSendLinkButton();
        return new HomePage(driver);
    }

    @Step("Back to 'Sign in' modal")
    public SignInPage backToSignIn() {
        waitUntilElementClickable(backToSignInButton);
        backToSignInButton.click();
        return new SignInPage(driver);
    }

    @Step("Close 'Forgot password' modal")
    public HomePage close() {
        waitUntilElementClickable(closeButton);
        closeButton.click();
        return new HomePage(driver);
    }

    @Step("Sign in with Google")
    public void signInWithGoogle() {
        waitUntilElementClickable(googleSignInButton);
        googleSignInButton.click();
    }


}
