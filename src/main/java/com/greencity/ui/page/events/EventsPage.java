package com.greencity.ui.page.events;

import com.greencity.ui.component.EventCardComponent;
import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EventsPage extends BasePage {

    private static final String EVENTS_HASH = "/#/greenCity/events";

    @FindBy(css = "div.create button")
    private WebElement createEventButton;

    @FindBy(css = "input.mat-start-date")
    private WebElement dateRangeStartInput;

    @FindBy(css = "input.mat-end-date")
    private WebElement dateRangeEndInput;

    @FindBy(css = "button.reset")
    private WebElement resetFiltersButton;

    @FindBy(css = "div.active-filter-container p")
    private WebElement itemsFoundLabel;

    @FindBy(css = "app-events-list-item")
    private List<WebElement> eventCards;

    @FindBy(css = "div.change-view")
    private WebElement viewModeRoot;

    @Getter
    private final ViewModeToggleComponent viewModeToggle;

    public EventsPage(WebDriver driver) {
        super(driver);
        this.viewModeToggle = new ViewModeToggleComponent(driver, viewModeRoot);
    }

    public EventsPage open() {
        open(EVENTS_HASH);
        return this;
    }

    public CreateEventPage createEvent() {
        clickElement(createEventButton);
        return new CreateEventPage(driver);
    }

    public SignInModal createEventAsGuest() {
        clickElement(createEventButton);
        return new SignInModal(driver);
    }

    public EventsPage filterByEventTime(String value) {
        return selectFilterOption(UiMessage.EVENT_FILTER_TIME, value);
    }

    public EventsPage filterByLocation(String value) {
        return selectFilterOption(UiMessage.EVENT_FILTER_LOCATION, value);
    }

    public EventsPage filterByStatus(String value) {
        return selectFilterOption(UiMessage.EVENT_FILTER_STATUS, value);
    }

    public EventsPage filterByType(String value) {
        return selectFilterOption(UiMessage.EVENT_FILTER_TYPE, value);
    }

    public EventsPage setDateRange(String startDate, String endDate) {
        typeText(dateRangeStartInput, startDate);
        typeText(dateRangeEndInput, endDate);
        return this;
    }

    public EventsPage resetAllFilters() {
        clickElement(resetFiltersButton);
        return this;
    }

    public boolean isResetAllEnabled() {
        waitUntilElementVisible(resetFiltersButton);
        return resetFiltersButton.isEnabled();
    }

    public boolean isCreateEventDisplayed() {
        return isElementDisplayed(createEventButton);
    }

    public boolean isDateRangeDisplayed() {
        return isElementDisplayed(dateRangeStartInput);
    }

    public String getItemsFoundText() {
        return getElementText(itemsFoundLabel);
    }

    public EventDetailsPage openEventById(int eventId) {
        open(EVENTS_HASH + "/" + eventId);
        return new EventDetailsPage(driver);
    }

    public EventCardComponent getEventCard(int index) {
        return new EventCardComponent(driver, getVisibleItem(eventCards, index));
    }

    public EventDetailsPage openEventByIndex(int index) {
        return getEventCard(index).openEventDetails();
    }

    private EventsPage selectFilterOption(UiMessage filterLabel, String value) {
        clickBy(By.xpath("//mat-label[normalize-space()=" + xpathLiteral(filterLabel.text())
                + "]/following-sibling::mat-select"));
        clickBy(By.xpath("//mat-option[contains(normalize-space(.), " + xpathLiteral(value) + ")]"));
        return this;
    }
}
