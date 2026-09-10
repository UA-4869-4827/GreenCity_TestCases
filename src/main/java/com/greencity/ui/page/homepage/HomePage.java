package com.greencity.ui.page.homepage;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.profile.ProfilePage;
import com.greencity.ui.page.ubscourier.UbsCourierPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final String GREEN_CITY_HOME_HASH = "/#/greenCity";

//    Hero Section

    @FindBy(css = "#header-left h1")
    private WebElement heroTitle;

    @FindBy(css = "#header-left p")
    private WebElement heroDescription;

    @FindBy(css = "#header-left button.primary-global-button")
    private WebElement heroStartHabitButton;

    @FindBy(css = "#guy-image")
    private WebElement heroPicture;


    //    Stats Section (Bags)
    @FindBy(css = "#stats > h2.section-caption")
    private WebElement statsTitle;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(1) h3")
    private WebElement bagsCounterLabel;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(1) h3 span")
    private WebElement bagsCounterValue;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(1) p")
    private WebElement bagsQuestionText;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(1) button.primary-global-button")
    private WebElement bagsStartHabitButton;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(1) a[routerlink='/places']")
    private WebElement buyEcoBagsLink;

    //    Stats Section (Cups)
    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(2) h3")
    private WebElement cupsCounterLabel;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(2) h3 span")
    private WebElement cupsCounterValue;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(2) p")
    private WebElement cupsQuestionText;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(2) button.primary-global-button")
    private WebElement cupsStartHabitButton;

    @FindBy(css = "#stat-rows app-stat-row:nth-of-type(2) a[routerlink='/places']")
    private WebElement cupDiscountPlacesLink;

    //    Eco News Section
    @FindBy(css = "#events h2.section-caption")
    private WebElement ecoNewsTitle;

    @FindBy(css = "#events a.centered")
    private WebElement readAllNewsLink;

    //    Subscription Section
    @FindBy(css = "#qr-code-wrapper img")
    private WebElement qrCodeImage;

    @FindBy(css = "#form-wrapper h2")
    private WebElement newsletterTitle;

    @FindBy(css = "#form-wrapper p")
    private WebElement newsletterDescription;

    @FindBy(css = "#subscription input[type='email']")
    private WebElement newsletterEmailInput;

    @FindBy(css = "#validation-error")
    private WebElement emailValidationError;

    @FindBy(css = "#subscription button.primary-global-button")
    private WebElement subscribeButton;

    @FindBy(css = ".mat-mdc-snack-bar-container.success-snackbar div[matsnackbarlabel]")
    private WebElement successfulSubscriptionMessage;

    @FindBy(css = ".mat-mdc-snack-bar-container.error-snackbar div[matsnackbarlabel]")
    private WebElement errorSubscriptionMessage;

//    Methods

    public HomePage open() {
        open(GREEN_CITY_HOME_HASH);
        return this;
    }

    public String getHeroTitleText() {
        return getElementText(heroTitle);
    }

    public String getHeroDescriptionText() {
        return getElementText(heroDescription);
    }

    public boolean isHeroDescriptionTextVisible() {
        return isElementDisplayed(heroDescription);
    }

    public boolean isHeroPictureVisible() {
        waitUntilElementVisible(heroPicture);
        return isElementDisplayed(heroPicture);
    }

    public boolean isHeroStartHabitButtonVisible() {
        return isElementDisplayed(heroStartHabitButton);
    }

    public String getHeroStartHabitButtonText() {
        return getElementText(heroStartHabitButton);
    }

    public SignInModal clickHeroStartHabitButtonAsGuest() {
        clickElement(heroStartHabitButton);
        waitForPageToLoad();
        return new SignInModal(driver);
    }

    public ProfilePage clickHeroStartHabitButton() {
        clickElement(heroStartHabitButton);
        return new ProfilePage(driver);
    }

    public String getStatsHeadingText() {
        return getElementText(statsTitle);
    }

    public boolean isStatsHeadingVisible() {
        return isElementDisplayed(statsTitle);
    }

    public String getBagsCounterValue() {
        return getElementText(bagsCounterValue);
    }

    public boolean isBagsCounterLabelVisible() {
        scrollToElementWithJs(bagsCounterLabel);
        return isElementDisplayed(bagsCounterLabel);
    }

    public String getBagsCounterLabelText() {
        return getElementText(bagsCounterLabel);
    }

    public String getBagsQuestionText() {
        return getElementText(bagsQuestionText);
    }

    public boolean isBagsQuestionTextVisible() {
        scrollToElementWithJs(bagsQuestionText);
        return isElementDisplayed(bagsQuestionText);
    }

    public SignInModal clickBagsStartHabitButtonAsGuest() {
        clickElement(bagsStartHabitButton);
        waitForPageToLoad();
        return new SignInModal(driver);
    }

    public ProfilePage clickBagsStartHabitButton() {
        clickElement(bagsStartHabitButton);
        return new ProfilePage(driver);
    }

    public boolean isBagsStartHabitButtonVisible() {
        scrollToElementWithJs(bagsStartHabitButton);
        return isElementDisplayed(bagsStartHabitButton);
    }

    public String getBagsStartHabitButtonText() {
        return getElementText(bagsStartHabitButton);
    }

    public String getCupsCounterValue() {
        return getElementText(cupsCounterValue);
    }

    public boolean isCupsCounterLabelVisible() {
        scrollToElementWithJs(cupsCounterLabel);
        return isElementDisplayed(cupsCounterLabel);
    }

    public String getCupsCounterLabelText() {
        return getElementText(cupsCounterLabel);
    }

    public String getCupsQuestionText() {
        return getElementText(cupsQuestionText);
    }

    public boolean isCupsQuestionTextVisible() {
        scrollToElementWithJs(cupsQuestionText);
        return isElementDisplayed(cupsQuestionText);
    }

    public boolean isCupsStartHabitButtonVisible() {
        scrollToElementWithJs(cupsStartHabitButton);
        return isElementDisplayed(cupsStartHabitButton);
    }

    public String getCupsStartHabitButtonText() {
        return getElementText(cupsStartHabitButton);
    }

    public SignInModal clickCupsStartHabitButtonAsGuest() {
        clickElement(cupsStartHabitButton);
        return new SignInModal(driver);
    }

    public ProfilePage clickCupsStartHabitButton() {
        clickElement(cupsStartHabitButton);
        return new ProfilePage(driver);
    }

    public UbsCourierPage openEcoBagsPlaces() {
        clickElement(buyEcoBagsLink);
        return new UbsCourierPage(driver);
    }

    public UbsCourierPage openCupDiscountPlaces() {
        clickElement(cupDiscountPlacesLink);
        return new UbsCourierPage(driver);
    }

    public String getEcoNewsTitleText() {
        return getElementText(ecoNewsTitle);
    }

    public EcoNewsPage openAllNews() {
        clickElement(readAllNewsLink);
        return new EcoNewsPage(driver);
    }

    public void enterNewsletterEmail(String email) {
        typeText(newsletterEmailInput, email);
    }

    public void clickSubscribeButton() {
        clickElement(subscribeButton);
    }

    public boolean isSubscribeButtonVisible() {
        scrollToElementWithJs(subscribeButton);
        return isElementDisplayed(subscribeButton);
    }

    public String getSubscribeButtonText() {
        return getElementText(subscribeButton);
    }

    public void subscribe(String email) {
        enterNewsletterEmail(email);
        clickSubscribeButton();
    }

    public String getNewsletterTitle() {
        return getElementText(newsletterTitle);
    }

    public boolean isNewsletterTitleVisible() {
        scrollToElementWithJs(newsletterTitle);
        return isElementDisplayed(newsletterTitle);
    }

    public String getNewsletterDescriptionText() {
        return getElementText(newsletterDescription);
    }

    public boolean isNewsletterDescriptionVisible() {
        scrollToElementWithJs(newsletterDescription);
        return isElementDisplayed(newsletterDescription);
    }

    public String getEmailPlaceholderText() {
        return getElementAttribute(newsletterEmailInput, "placeholder");
    }

    public boolean isEmailValidationErrorDisplayed() {
        return isElementDisplayed(emailValidationError);
    }

    public String getEmailValidationErrorText() {
        return getElementText(emailValidationError);
    }

    public boolean isQrCodeDisplayed() {
        scrollToElementWithJs(qrCodeImage);
        return isElementDisplayed(qrCodeImage);
    }

    public String getSuccessfulSubscriptionMessage() {
        return getElementText(successfulSubscriptionMessage);
    }

    public boolean isSuccessSubscriptionMessageVisible() {
        return isElementDisplayed(successfulSubscriptionMessage);
    }

    public String getErrorSubscriptionMessage() {
        return getElementText(errorSubscriptionMessage);
    }

    public boolean isErrorSubscriptionMessageVisible() {
        return isElementDisplayed(errorSubscriptionMessage);
    }
}
