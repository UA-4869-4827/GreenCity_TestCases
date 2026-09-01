package com.greencity.ui.page.places;

import com.greencity.ui.component.MoreOptionsMenu;
import com.greencity.ui.modal.AddPlaceModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PlacesPage extends BasePage {

    private static final String PLACES_HASH = "/#/greenCity/places";

    public enum PlacesFilter {
        SHOPS("Shops"),
        RESTAURANTS("Restaurants"),
        RECYCLING_POINTS("Recycling points"),
        EVENTS("Events");

        private final String text;

        PlacesFilter(String text) {
            this.text = text;
        }

        String getText() {
            return text;
        }
    }

    @FindBy(css = "input[name='search']")
    private WebElement searchInput;

    @FindBy(css = "input.choose-location-input")
    private WebElement locationInput;

    @FindBy(css = "app-more-options-filter > a.custom-chip.global-tag")
    private WebElement moreOptionsButton;

    @FindBy(css = "div.search > button.secondary-global-button.m-btn")
    private WebElement addPlaceButton;

    @FindBy(css = "div.gm-style-iw")
    private WebElement placeInfoWindow;

    @FindBy(css = "div.gm-style-iw span.title")
    private WebElement placeTitle;

    @FindBy(css = "div.gm-style-iw span.address")
    private WebElement placeAddress;

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

    public PlacesPage filterBy(PlacesFilter filter) {
        WebElement filterButton = driver.findElement(
                By.xpath("//app-tag-filter//button[contains(@class,'tag-button')]"
                        + "//span[contains(@class,'text') and normalize-space()='"
                        + filter.getText() + "']")
        );

        clickElement(filterButton);
        return this;
    }

    public MoreOptionsMenu openMoreOptions() {
        clickElement(moreOptionsButton);
        return new MoreOptionsMenu(driver);
    }

    public AddPlaceModal addPlace() {
        clickElement(addPlaceButton);
        return new AddPlaceModal(driver);
    }

    public boolean isPlaceInfoWindowDisplayed() {
        return isElementDisplayed(placeInfoWindow);
    }

    public String getPlaceTitle() {
        return getElementText(placeTitle);
    }

    public String getPlaceAddress() {
        return getElementText(placeAddress);
    }
}