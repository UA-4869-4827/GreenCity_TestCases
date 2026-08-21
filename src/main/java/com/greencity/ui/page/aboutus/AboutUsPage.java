package com.greencity.ui.page.aboutus;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.places.PlacesPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AboutUsPage extends BasePage {

    private static final String ABOUT_US_HASH = "/#/greenCity/about";

    @FindBy(xpath = "//h2[contains(normalize-space(.), 'About Us')]/following-sibling::button")
    private WebElement formHabitFromAboutUsButton;

    @FindBy(xpath = "//h2[contains(normalize-space(.), 'Our vision')]/following-sibling::button")
    private WebElement formHabitFromOurVisionButton;

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
        String currentUrl = driver.getCurrentUrl();
        String origin = currentUrl.contains("#")
                ? currentUrl.substring(0, currentUrl.indexOf('#'))
                : currentUrl;
        origin = origin.replaceAll("/$", "");
        driver.get(origin + ABOUT_US_HASH);
        waitForPageToLoad(10);
        return this;
    }

    public SignInModal formHabitFromAboutUsHeading() {
        clickElement(formHabitFromAboutUsButton);
        return new SignInModal(driver);
    }

    public SignInModal formHabitFromOurVisionHeading() {
        clickElement(formHabitFromOurVisionButton);
        return new SignInModal(driver);
    }

    public PlacesPage findEcoPlaces() {
        clickElement(findEcoPlacesLink);
        return new PlacesPage(driver);
    }

    public SignInModal findPeopleFromEcoProductsHeading() {
        clickElement(findPeopleFromEcoProductsLink);
        return new SignInModal(driver);
    }

    public EcoNewsPage getInspired() {
        clickElement(getInspiredLink);
        return new EcoNewsPage(driver);
    }

    public SignInModal findPeopleFromIDontFeelHeading() {
        clickElement(findPeopleFromIDontFeelLink);
        return new SignInModal(driver);
    }
}
