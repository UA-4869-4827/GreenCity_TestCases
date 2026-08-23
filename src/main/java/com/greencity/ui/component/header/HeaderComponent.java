package com.greencity.ui.component.header;

import com.greencity.ui.component.BaseComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.modal.SignUpModal;
import com.greencity.ui.page.EcoNews.EcoNewsPage;
import com.greencity.ui.page.aboutus.AboutUsPage;
import com.greencity.ui.page.events.EventsPage;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.ui.page.places.PlacesPage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HeaderComponent extends BaseComponent {

    @Getter
    @FindBy(xpath = ".//img[@src='assets/img/logo.svg']")
    private WebElement logo;

    private By logoLink = By.cssSelector("a.header_logo");

    private By ecoNewsLink = By.xpath("//header//a[contains(@class,'url-name') and normalize-space()='Eco news']");
    private By eventsLink = By.xpath("//header//a[contains(@class,'url-name') and normalize-space()='Events']");
    private By placesLink = By.xpath("//header//a[contains(@class,'url-name') and normalize-space()='Places']");
    private By aboutUsLink = By.xpath("//header//a[contains(@class,'url-name') and normalize-space()='About us']");
    private By mySpaceLink = By.xpath("//header//a[contains(@class,'url-name') and normalize-space()='My space']");

    private By searchIcon = By.cssSelector("header li.search-icon");
    private By languageSwitcher = By.cssSelector("header ul.header_lang-switcher-wrp");

    private By signInLink = By.cssSelector("header a.header_sign-in-link");
    private By signUpLink = By.cssSelector("header li.header_sign-up-link");
    private By userProfileIcon = By.cssSelector("header img[alt='sing in button']");

    private By userName = By.cssSelector("header #header_user-wrp li.user-name");
    private By userMenu = By.cssSelector("header #header_user-wrp");
    private By bookmarkIcon = By.cssSelector("header li.bookmark-icon");
    private By notificationIcon = By.cssSelector("header li.notification-icon");
    private By signOutLink = By.cssSelector("header #header_user-wrp li[aria-label='sign-out'] a");

    public HeaderComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public boolean isLoggedIn() {
        return isElementDisplayed(userName) && !isElementPresent(signInLink);
    }

    public boolean isSignInDisplayed() {
        return isElementDisplayed(signInLink);
    }

    public boolean isSignUpDisplayed() {
        return isElementDisplayed(signUpLink);
    }

    public boolean isUserNameDisplayed() {
        return isElementDisplayed(userName);
    }

    public boolean isBookmarkDisplayed() {
        return isElementDisplayed(bookmarkIcon);
    }

    public boolean isNotificationDisplayed() {
        return isElementDisplayed(notificationIcon);
    }

    public boolean isSearchDisplayed() {
        return isElementDisplayed(searchIcon);
    }

    public String getUserName() {
        return getText(userName);
    }

    public HomePage clickLogo() {
        click(logoLink);
        return new HomePage(driver);
    }

    public EcoNewsPage openEcoNews() {
        click(ecoNewsLink);
        return new EcoNewsPage(driver);
    }

    public EventsPage openEvents() {
        click(eventsLink);
        return new EventsPage(driver);
    }

    public PlacesPage openPlaces() {
        click(placesLink);
        return new PlacesPage(driver);
    }

    public AboutUsPage openAboutUs() {
        click(aboutUsLink);
        return new AboutUsPage(driver);
    }

    public SignInModal openMySpace() {
        click(mySpaceLink);
        return new SignInModal(driver);
    }

    public SignInModal clickSignIn() {
        click(signInLink);
        return new SignInModal(driver);
    }

    public SignInModal openUserProfile() {
        click(userProfileIcon);
        return new SignInModal(driver);
    }

    public SignUpModal openSignUp() {
        click(signUpLink);
        return new SignUpModal(driver);
    }

    public void openSearch() {
        click(searchIcon);
    }

    public EcoNewsPage openBookmarks() {
        click(bookmarkIcon);
        return new EcoNewsPage(driver);
    }

    public void openNotifications() {
        click(notificationIcon);
    }

    public HomePage signOut() {
        click(userMenu);
        click(signOutLink);
        return new HomePage(driver);
    }

    public void selectLanguage(String language) {
        click(languageSwitcher);
        click(By.xpath(
                "//header//li[contains(@class,'lang-option')][.//span[normalize-space()='" + language + "']]"));
    }
}
