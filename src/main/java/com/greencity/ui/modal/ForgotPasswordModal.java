package com.greencity.ui.modal;

import com.greencity.ui.page.homepage.HomePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ForgotPasswordModal extends BaseModal<ForgotPasswordModal> {

    @FindBy(css = "app-restore-password h1")
    private WebElement modalTitle;

    @FindBy(css = "app-restore-password h2")
    private WebElement modalSubtitle;

    @FindBy(css = "form.restore-password-form button[type='submit']")
    private WebElement sendLinkButton;

    @FindBy(css = "div.mentioned-password a.green-link")
    private WebElement backToSignInButton;

    @FindBy(css = "form.restore-password-form .validation-email-error")
    private WebElement emailErrorMessage;

    public ForgotPasswordModal(WebDriver driver) {
        super(driver);
    }

    @Override
    protected ForgotPasswordModal self() {
        return this;
    }

    @Step("Click 'Send link' button")
    public ForgotPasswordModal clickSendLinkButton() {
        return clickOn(sendLinkButton);
    }

    public String getModalTitleText() {
        return getElementText(modalTitle);
    }

    public String getModalSubtitleText() {
        return getElementText(modalSubtitle);
    }

    public String getEmailErrorMessageText() {
        return getElementText(emailErrorMessage);
    }

    public boolean isSendLinkButtonEnabled() {
        return sendLinkButton.isEnabled();
    }

    @Step("Submit login link for email: {email}")
    public HomePage submitLoginLink(String email) {
        enterEmail(email);
        clickSendLinkButton();
        waitUntilClosed();
        return new HomePage(driver);
    }

    @Step("Back to 'Sign in' modal")
    public SignInModal backToSignIn() {
        clickElement(backToSignInButton);
        return new SignInModal(driver);
    }
}
