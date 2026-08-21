package com.greencity.ui.component.header;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HeaderComponent extends BaseComponent {

    @Getter
    @FindBy(xpath = ".//img[@src='assets/img/logo.svg']")
    private WebElement logo;

    @Getter
    @FindBy(xpath = ".//a[contains(@href,'/greenCity/news')]")
    private WebElement ecoNewsLink;

    @Getter
    @FindBy(xpath = ".//a[contains(@href,'/greenCity/events')]")
    private WebElement eventsLink;

    @Getter
    @FindBy(xpath = ".//a[contains(@href,'/greenCity/places')]")
    private WebElement placesLink;

    @Getter
    @FindBy(xpath = ".//a[contains(@href,'/greenCity/about')]")
    private WebElement aboutUsLink;

    @Getter
    @FindBy(xpath = ".//a[contains(@class,'header_sign-in-link')]")
    private WebElement signInLink;

    @Getter
    @FindBy(xpath = ".//*[self::a or self::button or self::span][normalize-space()='Sign up']")
    private WebElement signUpLink;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public HeaderComponent clickLogo() {
        clickElement(logo);
        return this;
    }

    public HeaderComponent openEcoNews() {
        clickElement(ecoNewsLink);
        return this;
    }

    public HeaderComponent openEvents() {
        clickElement(eventsLink);
        return this;
    }

    public HeaderComponent openPlaces() {
        clickElement(placesLink);
        return this;
    }

    public HeaderComponent openAboutUs() {
        clickElement(aboutUsLink);
        return this;
    }

    public SignInModal clickSignIn() {
        clickElement(signInLink);
        return new SignInModal(driver);
    }

    public SignUpModal clickSignUp() {
        clickElement(signUpLink);
        return new SignUpModal(driver);
    }
}
