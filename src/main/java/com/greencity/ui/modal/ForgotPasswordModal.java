package com.greencity.ui.modal;

import com.greencity.ui.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordModal extends BaseModal<ForgotPasswordModal> {

    @FindBy(css = "app-restore-password h1")
    private WebElement modalTitle;

    @FindBy(css = "app-restore-password h2")
    private WebElement modalSubtitle;

    @FindBy(css = "button.green-send-btn[type='submit']")
    private WebElement sendLinkButton;

    @FindBy(css = "div.mentioned-password a.green-link")
    private WebElement backToSignInButton;

//
//@FindBy(css = "form.restore-password-form .validation-email-error")
//    private WebElement emailErrorMessage;

    @FindBy(css = ".validation-email-error")
    private WebElement emailErrorMessage;

    @FindBy(css = "#email")
    private WebElement emailField;

    @FindBy(css = "button.google-sign-in")
    private WebElement googleSignInButton;

    public ForgotPasswordModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ForgotPasswordModal self() {
        return this;
    }

    //    @Step("Click 'Send link' button")
//    public void clickSendLinkButton() {
//        clickElement(sendLinkButton);
//    }
    @Step("Click 'Send link' button")
    public ForgotPasswordModal clickSendLinkButton() {
        clickElement(sendLinkButton);
        return this;
    }

    public String getModalTitleText() {
        return getElementText(modalTitle);
    }

    public String getModalSubtitleText() {
        return getElementText(modalSubtitle);
    }

    public boolean isEmailFieldDisplayed() {
        return isElementDisplayed(emailField);
    }

    public String getEmailErrorMessageText() {
        return getElementText(emailErrorMessage);
    }

    public boolean isEmailErrorMessageVisible() {
        waitUntilElementVisible(emailErrorMessage);
        return isElementDisplayed(emailErrorMessage);
    }

    public boolean isSendLinkButtonDisplayed() {
        waitUntilElementVisible(sendLinkButton);
        return isElementDisplayed(sendLinkButton);
    }

    public String getSendLinkButtonText() {
        return getElementText(sendLinkButton);
    }

    public boolean isSendLinkButtonEnabled() {
        return sendLinkButton.isEnabled();
    }

    public boolean isGoogleSignInDisplayed() {
        return isElementDisplayed(googleSignInButton);
    }

    public String getGoogleSignInText() {
        return getElementText(googleSignInButton);
    }

    @Step("Submit login link for email: {email}")
    public <P extends BasePage> P submitLoginLink(String email, Class<P> pageClass) {
        enterEmail(email);
        clickSendLinkButton();
        waitUntilClosed();
        return openPage(pageClass);
    }

    public ForgotPasswordModal enterEmail(String email) {
        typeText(emailField, email);
        emailField.sendKeys(Keys.TAB);
        return this;
    }

    @Step("Back to 'Sign in' modal")
    public SignInModal backToSignIn() {
        clickElement(backToSignInButton);
        return new SignInModal(driver);
    }

    public boolean isBackToSignInVisible() {
        return isElementDisplayed(backToSignInButton);
    }

    public String getBackToSignInText() {
        return getElementText(backToSignInButton);
    }
}
