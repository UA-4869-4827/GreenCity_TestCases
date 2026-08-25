package com.greencity.ui.page.places;

import com.greencity.ui.component.MoreOptionsMenu;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PlacesPage extends BasePage {

    private static final String PLACES_HASH = "/#/greenCity/places";

    public enum PlacesFilter {
        SHOPS(0),
        RESTAURANTS(1),
        RECYCLING_POINTS(2),
        EVENTS(3),
        SAVED_PLACES(4);

        private final int index;

        PlacesFilter(int index) {
            this.index = index;
        }

        WebElement getElement(List<WebElement> filters) {
            return filters.get(index);
        }
    }

    @FindBy(css = "input[name='search']")
    private WebElement searchInput;

    @FindBy(css = "input.choose-location-input")
    private WebElement locationInput;

    @FindBy(css = "app-tag-filter div.ul-eco-buttons button.tag-button")
    private List<WebElement> filterButtons;

    @FindBy(css = "app-more-options-filter > a.custom-chip.global-tag")
    private WebElement moreOptionsButton;

    @FindBy(css = "div.search > button.secondary-global-button.m-btn")
    private WebElement addPlaceButton;

    @FindBy(css = "div.gm-style-iw[role='dialog']")
    private WebElement placeInfoWindow;

    @FindBy(css = "div.gm-style-iw[role='dialog'] span.title")
    private WebElement placeTitle;

    @FindBy(css = "div.gm-style-iw[role='dialog'] span.address")
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
        clickElement(filter.getElement(filterButtons));
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
}