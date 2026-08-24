package com.greencity.ui.page.homepage;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.EcoNewsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final String GREEN_CITY_HOME_HASH = "/#/greenCity";

//    Main Section

    @FindBy(css = "#header-left h1")
    private WebElement mainTitle;

    @FindBy(css = "#header-left p")
    private WebElement mainDescription;

    @FindBy(css = "#header-left button.primary-global-button")
    private WebElement mainStartHabitButton;

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

    public String getMainTitleText() {
        return getElementText(mainTitle);
    }

    public String getMainDescriptionText() {
        return getElementText(mainDescription);
    }

    public SignInModal clickMainStartHabitButtonGuest() {
        clickElementWithJs(mainStartHabitButton);
        return new SignInModal(driver);
    }

    public ProfilePage clickMainStartHabitButtonLoggedIn() {
        clickElementWithJs(mainStartHabitButton);
        return new ProfilePage(driver);
    }

    public String getStatsTitleText() {
        return getElementText(statsTitle);
    }

    public String getBagsCounterValue() {
        return getElementText(bagsCounterValue);
    }

    public String getBagsCounterLabelText() {
        return getElementText(bagsCounterLabel);
    }

    public String getBagsQuestionText() {
        return getElementText(bagsQuestionText);
    }

    public String getCupsCounterValue() {
        return getElementText(cupsCounterValue);
    }

    public SignInModal clickBagsStartHabitButtonGuest() {
        clickElementWithJs(bagsStartHabitButton);
        return new SignInModal(driver);
    }
    public ProfilePage clickBagsStartHabitButtonLoggedIn() {
        clickElementWithJs(bagsStartHabitButton);
        return new ProfilePage(driver);
    }

    public String getCupsCounterLabelText() {
        return getElementText(cupsCounterLabel);
    }

    public String getCupsQuestionText() {
        return getElementText(cupsQuestionText);
    }

    public SignInModal clickCupsStartHabitButtonGuest() {
        clickElementWithJs(cupsStartHabitButton);
        return new SignInModal(driver);
    }
    public ProfilePage clickCupsStartHabitButtonLoggedIn() {
        clickElementWithJs(cupsStartHabitButton);
        return new ProfilePage(driver);
    }

    public UbsCourierPage openEcoBagsPlaces() {
        clickElementWithJs(buyEcoBagsLink);
        return new UbsCourierPage(driver);
    }

    public UbsCourierPage openCupDiscountPlaces() {
        clickElementWithJs(cupDiscountPlacesLink);
        return new UbsCourierPage(driver);
    }

    public String getEcoNewsTitleText() {
        return getElementText(ecoNewsTitle);
    }

    public EcoNewsPage openAllNews() {
        clickElementWithJs(readAllNewsLink);
        return new EcoNewsPage(driver);
    }

    public void enterNewsletterEmail(String email) {
        clickElementWithJs(newsletterEmailInput);
        typeText(newsletterEmailInput, email);
    }

    public void clickSubscribeButton() {
        clickElementWithJs(subscribeButton);
    }

    public void subscribe(String email) {
        enterNewsletterEmail(email);
        clickSubscribeButton();
    }

    public String getNewsletterTitle() {
        return getElementText(newsletterTitle);
    }

    public String getNewsletterDescriptionText() {
        return getElementText(newsletterDescription);
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
        return isElementDisplayed(qrCodeImage);
    }

    public String getSuccessfulSubscriptionMessage() {
        return getElementText(successfulSubscriptionMessage);
    }

    public String getErrorSubscriptionMessage() {
        return getElementText(errorSubscriptionMessage);
    }
}
