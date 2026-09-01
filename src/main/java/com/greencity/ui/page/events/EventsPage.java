package com.greencity.ui.page.events;

import com.greencity.ui.component.EventCardComponent;
import com.greencity.ui.component.ViewModeToggleComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EventsPage extends BasePage {

    private static final String EVENTS_HASH = "/#/greenCity/events";

    @FindBy(css = "div.create button")
    private WebElement createEventButton;

    @FindBy(xpath = "//mat-label[normalize-space()='Event time']/following-sibling::mat-select")
    private WebElement eventTimeFilter;

    @FindBy(xpath = "//mat-label[normalize-space()='Location']/following-sibling::mat-select")
    private WebElement locationFilter;

    @FindBy(xpath = "//mat-label[normalize-space()='Status']/following-sibling::mat-select")
    private WebElement statusFilter;

    @FindBy(xpath = "//mat-label[normalize-space()='Type']/following-sibling::mat-select")
    private WebElement typeFilter;

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

    private ViewModeToggleComponent viewModeToggle;

    public EventsPage(WebDriver driver) {
        super(driver);
        this.viewModeToggle = new ViewModeToggleComponent(driver, viewModeRoot);
    }

    public EventsPage open() {
        String currentUrl = driver.getCurrentUrl();
        String origin = currentUrl.contains("#")
                ? currentUrl.substring(0, currentUrl.indexOf('#'))
                : currentUrl;
        origin = origin.replaceAll("/$", "");
        driver.get(origin + EVENTS_HASH);
        waitForPageToLoad(10);
        return this;
    }

    public ViewModeToggleComponent getViewModeToggle() {
        return viewModeToggle;
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
        return selectFilterOption(eventTimeFilter, value);
    }

    public EventsPage filterByLocation(String value) {
        return selectFilterOption(locationFilter, value);
    }

    public EventsPage filterByStatus(String value) {
        return selectFilterOption(statusFilter, value);
    }

    public EventsPage filterByType(String value) {
        return selectFilterOption(typeFilter, value);
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

    public String getItemsFoundText() {
        return getElementText(itemsFoundLabel);
    }

    public EventCardComponent getEventCard(int index) {
        waitUntilAllElementsVisible(eventCards);
        return new EventCardComponent(driver, eventCards.get(index));
    }

    public EventDetailsPage openEventByIndex(int index) {
        return getEventCard(index).openEventDetails();
    }

    private EventsPage selectFilterOption(WebElement filter, String value) {
        clickElement(filter);
        WebElement option = driver.findElement(
                By.xpath("//mat-option[contains(normalize-space(.), " + xpathLiteral(value) + ")]"));
        clickElement(option);
        return this;
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'", -1);
        StringBuilder concat = new StringBuilder("concat('");
        concat.append(String.join("', \"'\", '", parts)).append("')");
        return concat.toString();
    }
}
