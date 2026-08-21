package com.greencity.ui.page.places;

import com.greencity.ui.component.MoreOptionsMenu;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PlacesPage extends BasePage {

    private static final String PLACES_HASH = "/#/greenCity/places";

    @FindBy(css = "input[name='search']")
    private WebElement searchInput;

    @FindBy(css = "input.choose-location-input")
    private WebElement locationInput;

    @FindBy(xpath = "//button[.//span[normalize-space()='Shops']]")
    private WebElement shopsButton;

    @FindBy(xpath = "//button[.//span[normalize-space()='Restaurants']]")
    private WebElement restaurantsButton;

    @FindBy(xpath = "//button[.//span[normalize-space()='Recycling points']]")
    private WebElement recyclingPointsButton;

    @FindBy(xpath = "//button[.//span[normalize-space()='Events']]")
    private WebElement eventsButton;

    @FindBy(css = "app-more-options-filter a.mat-mdc-menu-trigger")
    private WebElement moreOptionsButton;

    @FindBy(xpath = "//button[normalize-space()='Add place']")
    private WebElement addPlaceButton;

    public PlacesPage(WebDriver driver) {
        super(driver);
    }

    public PlacesPage open() {
        String currentUrl = driver.getCurrentUrl();
        String origin = currentUrl.contains("#")
                ? currentUrl.substring(0, currentUrl.indexOf('#'))
                : currentUrl;
        origin = origin.replaceAll("/$", "");
        driver.get(origin + PLACES_HASH);
        waitForPageToLoad(10);
        return this;
    }

    public PlacesPage searchForPlace(String query) {
        typeText(searchInput, query);
        return this;
    }

    public PlacesPage chooseLocation(String location) {
        typeText(locationInput, location);
        return this;
    }

    public PlacesPage filterByShops() {
        clickElement(shopsButton);
        return this;
    }

    public PlacesPage filterByRestaurants() {
        clickElement(restaurantsButton);
        return this;
    }

    public PlacesPage filterByRecyclingPoints() {
        clickElement(recyclingPointsButton);
        return this;
    }

    public PlacesPage filterByEvents() {
        clickElement(eventsButton);
        return this;
    }

    public MoreOptionsMenu openMoreOptions() {
        clickElement(moreOptionsButton);
        return new MoreOptionsMenu(driver);
    }

    public SignInModal addPlace() {
        clickElement(addPlaceButton);
        return new SignInModal(driver);
    }
}
