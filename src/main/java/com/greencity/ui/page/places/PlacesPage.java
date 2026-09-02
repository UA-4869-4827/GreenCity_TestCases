package com.greencity.ui.page.places;

import com.greencity.ui.component.MoreOptionsMenu;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.AddPlaceModal;
import com.greencity.ui.page.BasePage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PlacesPage extends BasePage {

    private static final String PLACES_HASH = "/#/greenCity/places";

    @Getter
    @RequiredArgsConstructor
    public enum PlacesFilter {
        SHOPS(UiMessage.PLACES_FILTER_SHOPS),
        RESTAURANTS(UiMessage.PLACES_FILTER_RESTAURANTS),
        RECYCLING_POINTS(UiMessage.PLACES_FILTER_RECYCLING_POINTS),
        EVENTS(UiMessage.PLACES_FILTER_EVENTS),
        SAVED_PLACES(UiMessage.PLACES_FILTER_SAVED_PLACES);

        private final UiMessage message;

        public String getText() {
            return message.text();
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

    @FindBy(css = "div.content-info-pop-up")
    private WebElement placeInfoWindow;

    @FindBy(css = "div.content-info-pop-up h6.content-title")
    private WebElement placeTitle;

    @FindBy(css = "div.content-info-pop-up h6.content-address")
    private WebElement placeAddress;

    public PlacesPage(WebDriver driver) {
        super(driver);
    }

    public PlacesPage open() {
        open(PLACES_HASH);
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
        clickBy(By.xpath("//app-tag-filter//button[contains(@class,'tag-button')]"
                + "//span[contains(@class,'text') and normalize-space()="
                + xpathLiteral(filter.getText()) + "]"));
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
