package com.greencity.ui.component.header;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.locale.LocaleContext;
import com.greencity.ui.locale.UiLocale;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.places.PlacesPage;
import com.greencity.ui.page.profile.ProfilePage;
import com.greencity.ui.page.ubscourier.UbsCourierPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class HeaderComponent extends BaseComponent {

    @FindBy(xpath = ".//img[@src='assets/img/logo.svg']")
    private WebElement logo;

    @FindBy(xpath = ".//a[contains(@href,'/greenCity/news')]")
    private WebElement ecoNewsLink;

    @FindBy(xpath = ".//a[contains(@href,'/greenCity/events')]")
    private WebElement eventsLink;

    @FindBy(xpath = ".//a[contains(@href,'/greenCity/places')]")
    private WebElement placesLink;

    @FindBy(xpath = ".//a[contains(@href,'/greenCity/about')]")
    private WebElement aboutUsLink;

    @FindBy(xpath = ".//a[contains(@href,'/greenCity/profile')]")
    private WebElement mySpaceLink;

    @FindBy(xpath = ".//a[contains(@href,'/ubs')]")
    private WebElement ubsCourierLink;

    @FindBy(xpath = ".//a[contains(@class,'header_sign-in-link')]")
    private WebElement signInLink;

    @FindBy(css = ".header_sign-up-link, .header_sign-up-btn")
    private WebElement signUpLink;

    @FindBy(xpath = ".//*[@id='header_user-wrp']")
    private WebElement userMenu;

    @FindBy(xpath = ".//*[@id='header_user-wrp']//li[contains(@class,'user-name')]")
    private WebElement userName;

    @FindBy(xpath = ".//*[@aria-label='sign-out']//a")
    private WebElement signOutLink;

    @FindBy(css = ".header_lang-switcher-wrp, .drop-down.header_lang, ul.header_add-lang")
    private WebElement languageSwitcher;

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public boolean isLogoDisplayed() {
        return isElementDisplayed(logo);
    }

    public boolean isSignInDisplayed() {
        return isElementDisplayed(signInLink);
    }

    public boolean isSignUpDisplayed() {
        return isElementDisplayed(signUpLink);
    }

    public HomePage clickLogo() {
        clickElement(logo);
        return new HomePage(driver);
    }

    public String getSignUpText() {
        return getElementText(signUpLink);
    }

    public List<String> getLanguageOptionLabels() {
        return rootElement.findElements(By.cssSelector("li.lang-option")).stream()
                .map(WebElement::getText)
                .map(String::trim)
                .filter(label -> !label.isEmpty())
                .toList();
    }

    public HeaderComponent openLanguageSwitcher() {
        clickElement(languageSwitcher);
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

    public SignInModal openMySpaceAsGuest() {
        clickElement(mySpaceLink);
        return new SignInModal(driver);
    }

    public UbsCourierPage openUbsCourier() {
        clickElement(ubsCourierLink);
        return new UbsCourierPage(driver);
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

    public HeaderComponent switchLanguage(UiLocale locale) {
        if (locale == LocaleContext.get()) {
            return this;
        }
        clickElement(languageSwitcher);
        clickElement(findIn(rootElement, By.xpath(".//*[normalize-space()="
                + xpathLiteral(locale.getHeaderLabel()) + "]")));
        LocaleContext.set(locale);
        waitForPageToLoad();
        return this;
    }
}
