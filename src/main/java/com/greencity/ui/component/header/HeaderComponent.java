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
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class HeaderComponent extends BaseComponent {
    private static final String LANGUAGE_OPTION =  "//li[contains(@class,'lang-option')][.//span[normalize-space()='%s']]";

    @Getter
    public enum Language {
        ENGLISH("En", "Sign up"),
        UKRAINIAN("Uk", "Зареєструватися");

        private final String text;
        private final String signUpText;

        Language(String text, String signUpText) {
            this.text = text;
            this.signUpText = signUpText;
        }
    }

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
    @FindBy(css = ".header_sign-up-btn")
    private WebElement signUpLink;

    @Getter
    @FindBy(css = "ul[aria-label='language switcher']")
    private WebElement languageSwitcher;

    @FindBy(css = "ul[aria-label='language switcher'] li.lang-option")
    private List<WebElement> languageOptions;

    @FindBy(xpath = ".//*[@id='header_user-wrp']")
    private WebElement userMenu;

    @FindBy(xpath = ".//*[@id='header_user-wrp']//li[contains(@class,'user-name')]")
    private WebElement userName;

    @FindBy(xpath = ".//*[@aria-label='sign-out']//a")
    private WebElement signOutLink;

    @FindBy(css = ".warning_massage")
    private WebElement warningMessage;

    @FindBy(css = ".warning_button_comment")
    private WebElement warningButton;

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

    private void waitForLanguageChanged(Language language) {
        wait.until(ExpectedConditions.textToBePresentInElement(
                signUpLink,
                language.getSignUpText()
        ));
    }

    public void selectLanguage(Language language) {
        openLanguageSwitcher();
        clickLanguageOption(language);
        waitForLanguageChanged(language);

    }

    private void clickLanguageOption(Language language) {
        By languageOption = By.xpath(
                String.format(LANGUAGE_OPTION, language.getText())
        );

        WebElement option = wait.until(
                ExpectedConditions.visibilityOfElementLocated(languageOption)
        );

        dispatchClick(option);
    }

    private void dispatchClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].dispatchEvent(new MouseEvent('click', " +
                        "{bubbles: true, cancelable: true, view: window}));",
                element
        );
    }

    public void openLanguageSwitcher() {
        if (!"true".equals(languageSwitcher.getAttribute("aria-expanded"))) {
            clickElement(languageSwitcher);
        }
    }

    public List<String> getAvailableLanguagesText() {
        return languageOptions.stream()
                .map(this::getElementText)
                .toList();
    }

    public boolean isLogoDisplayed() {
        waitUntilElementVisible(logo);
        return logo.isDisplayed();
    }

    public boolean isSignInDisplayed() {
        waitUntilElementVisible(signInLink);
        return signInLink.isDisplayed();
    }

    public boolean isSignUpDisplayed() {
        waitUntilElementVisible(signUpLink);
        return signUpLink.isDisplayed();
    }

    public boolean isLanguageSwitcherDisplayed() {
        waitUntilElementVisible(languageSwitcher);
        return languageSwitcher.isDisplayed();
    }

    public String getSignUpText() {
        return getElementText(signUpLink);
    }

    public boolean containsRawI18nKey() {
        String headerText = rootElement.getText();

        return headerText.contains("user.warning.");
    }

    public HeaderComponent scrollDown(int pixels) {
        new Actions(driver)
                .scrollByAmount(0, pixels)
                .perform();
        return this;
    }

    public void scrollBy(int x, int y) {
        new Actions(driver)
                .scrollByAmount(x, y)
                .perform();
    }

}
