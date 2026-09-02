package com.greencity.ui.component.header;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.page.places.PlacesPage;
import com.greencity.ui.page.profile.ProfilePage;
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
    @FindBy(xpath = ".//a[contains(@href,'/greenCity/profile')]")
    private WebElement mySpaceLink;

    @Getter
    @FindBy(xpath = ".//a[contains(@class,'header_sign-in-link')]")
    private WebElement signInLink;

    @Getter
    @FindBy(xpath = ".//*[self::a or self::button or self::span][normalize-space()='Sign up']")
    private WebElement signUpLink;

    @FindBy(xpath = ".//*[@id='header_user-wrp']")
    private WebElement userMenu;

    @FindBy(xpath = ".//*[@id='header_user-wrp']//li[contains(@class,'user-name')]")
    private WebElement userName;

    @FindBy(xpath = ".//*[@aria-label='sign-out']//a")
    private WebElement signOutLink;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public HeaderComponent clickLogo() {
        clickElement(logo);
        return this;
    }

    public EcoNewsPage openEcoNews() {
        clickElement(ecoNewsLink);
        return new EcoNewsPage(driver);
    }

    public EventsPage openEvents() {
        clickElement(eventsLink);
        return new EventsPage(driver);
    }

    public PlacesPage openPlaces() {
        clickElement(placesLink);
        return new PlacesPage(driver);
    }

    public AboutUsPage openAboutUs() {
        clickElement(aboutUsLink);
        return new AboutUsPage(driver);
    }

    public ProfilePage openMySpace() {
        clickElement(mySpaceLink);
        return new ProfilePage(driver);
    }

    public SignInModal clickSignIn() {
        clickElement(signInLink);
        return new SignInModal(driver);
    }

    public SignUpModal clickSignUp() {
        clickElement(signUpLink);
        return new SignUpModal(driver);
    }

    public boolean isLoggedIn() {
        return isElementDisplayed(userMenu);
    }

    public String getUserName() {
        return getElementText(userName);
    }

    public HeaderComponent openUserMenu() {
        clickElement(userMenu);
        return this;
    }

    public HeaderComponent signOut() {
        openUserMenu();
        clickElement(signOutLink);
        waitUntilElementVisible(signInLink);
        return this;
    }
}
