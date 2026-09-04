package com.greencity.ui.page.aboutus;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.friends.FriendsPage;
import com.greencity.ui.page.places.PlacesPage;
import com.greencity.ui.page.profile.ProfilePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutUsPage extends BasePage {

    private static final String ABOUT_US_HASH = "/#/greenCity/about";

    @FindBy(css = "a.vision-card__link[href*='/places']")
    private WebElement findEcoPlacesLink;

    @FindBy(xpath = "(//a[contains(@class,'vision-card__link') and contains(@href,'/friends')])[1]")
    private WebElement findPeopleFromEcoProductsLink;

    @FindBy(css = "a.vision-card__link[href*='/news']")
    private WebElement getInspiredLink;

    @FindBy(xpath = "(//a[contains(@class,'vision-card__link') and contains(@href,'/friends')])[2]")
    private WebElement findPeopleFromIDontFeelLink;

    public AboutUsPage(WebDriver driver) {
        super(driver);
    }

    public AboutUsPage open() {
        open(ABOUT_US_HASH);
        return this;
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(ABOUT_US_HASH);
    }

    public boolean isAboutUsHeadingDisplayed() {
        return isElementDisplayed(headingLocator(UiMessage.ABOUT_US_HEADER));
    }

    public boolean isOurVisionHeadingDisplayed() {
        return isElementDisplayed(headingLocator(UiMessage.ABOUT_US_VISION_HEADER));
    }

    public ProfilePage formHabitFromAboutUsHeading() {
        clickElement(habitButtonNextTo(UiMessage.ABOUT_US_HEADER));
        return new ProfilePage(driver);
    }

    public AboutUsPage formHabitFromAboutUsHeadingAsGuest() {
        clickElement(habitButtonNextTo(UiMessage.ABOUT_US_HEADER));
        return this;
    }

    public ProfilePage formHabitFromOurVisionHeading() {
        clickElement(habitButtonNextTo(UiMessage.ABOUT_US_VISION_HEADER));
        return new ProfilePage(driver);
    }

    public AboutUsPage formHabitFromOurVisionHeadingAsGuest() {
        clickElement(habitButtonNextTo(UiMessage.ABOUT_US_VISION_HEADER));
        return this;
    }

    public PlacesPage findEcoPlaces() {
        clickElement(findEcoPlacesLink);
        return new PlacesPage(driver);
    }

    public FriendsPage findPeopleFromEcoProductsHeading() {
        clickElement(findPeopleFromEcoProductsLink);
        return new FriendsPage(driver);
    }

    public SignInModal findPeopleFromEcoProductsHeadingAsGuest() {
        clickElement(findPeopleFromEcoProductsLink);
        return new SignInModal(driver);
    }

    public EcoNewsPage getInspired() {
        clickElement(getInspiredLink);
        return new EcoNewsPage(driver);
    }

    public FriendsPage findPeopleFromIDontFeelHeading() {
        clickElement(findPeopleFromIDontFeelLink);
        return new FriendsPage(driver);
    }

    public SignInModal findPeopleFromIDontFeelHeadingAsGuest() {
        clickElement(findPeopleFromIDontFeelLink);
        return new SignInModal(driver);
    }

    private WebElement habitButtonNextTo(UiMessage heading) {
        By locator = headingLocator(heading);
        waitUntilElementPresent(locator);
        return driver.findElement(locator).findElement(By.xpath("./following-sibling::button"));
    }

    private By headingLocator(UiMessage heading) {
        return byNormalizedTextContains("//h2", heading);
    }

}
