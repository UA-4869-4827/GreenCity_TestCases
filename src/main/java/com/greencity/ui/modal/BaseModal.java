package com.greencity.ui.modal;

import com.greencity.ui.Base;
import com.greencity.ui.page.homepage.HomePage;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public abstract class BaseModal<T extends BaseModal<T>> extends Base {

    @FindBy(css = "a.close-modal-window")
    protected WebElement closeButton;

    @FindBy(css = "app-google-btn button.google-sign-in")
    private WebElement googleButton;

    @FindBy(id = "email")
    protected WebElement emailInput;

    protected BaseModal(WebDriver driver) {
        super(driver);
        waitUntilElementVisible(closeButton);
    }

    protected abstract T self();

    @Step("Enter email: {email}")
    public T enterEmail(String email) {
        typeText(emailInput, email);
        return self();
    }

    @Step("Continue with Google")
    public void continueWithGoogle() {
        clickElement(googleButton);
    }

    @Step("Close modal")
    public HomePage close() {
        clickElement(closeButton);
        waitUntilElementInvisible(closeButton);
        return new HomePage(driver);
    }

    protected T enterInto(WebElement field, String text) {
        typeText(field, text);
        return self();
    }

    protected T clickOn(WebElement element) {
        clickElement(element);
        return self();
    }

    protected void waitUntilClosed() {
        waitUntilElementInvisible(closeButton);
    }
}
