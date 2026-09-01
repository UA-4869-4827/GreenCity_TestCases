package com.greencity.ui.page.events;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CreateEventPage extends BasePage {

    public CreateEventPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//mat-label[normalize-space()='Enter a name for the event']/ancestor::mat-form-field//input")
    private WebElement titleInput;

    @FindBy(css = "mat-select[formcontrolname='duration']")
    private WebElement durationSelect;

    @FindBy(xpath = "//mat-chip-listbox[@formcontrolname='tags']//mat-chip-option")
    private List<WebElement> typeChips;

    @FindBy(css = "mat-select[formcontrolname='open']")
    private WebElement eventTypeDropdown;

    @FindBy(xpath = "//mat-label[normalize-space()='Invite']/ancestor::mat-form-field//mat-select")
    private WebElement inviteDropdown;

    @FindBy(css = "div.ql-editor")
    private WebElement descriptionEditor;

    @FindBy(css = "input[formcontrolname='day']")
    private WebElement dateInput;

    @FindBy(css = "button.mat-calendar-previous-button")
    private WebElement previousMonthButton;

    @FindBy(css = "button.mat-calendar-next-button")
    private WebElement nextMonthButton;

    @FindBy(css = "td.mat-calendar-body-cell span.mat-calendar-body-cell-content")
    private List<WebElement> calendarDayCells;

    @FindBy(css = "input[formcontrolname='startTime']")
    private WebElement startTimeInput;

    @FindBy(css = "input[formcontrolname='finishTime']")
    private WebElement endTimeInput;

    @FindBy(css = "mat-checkbox[formcontrolname='allDay'] div.mdc-checkbox")
    private WebElement allDayCheckbox;


    @FindBy(xpath = "//mat-checkbox[.//label[normalize-space()='Place']]//div[contains(@class,'mdc-checkbox')]")
    private WebElement placeCheckbox;

    @FindBy(xpath = "//mat-checkbox[.//label[normalize-space()='Online']]//div[contains(@class,'mdc-checkbox')]")
    private WebElement onlineCheckbox;

    @FindBy(css = "input[formcontrolname='place']")
    private WebElement locationInput;

    @FindBy(xpath = "//mat-label[contains(@class,'link-title')]/ancestor::mat-form-field//input")
    private WebElement onlineLinkInput;

    @FindBy(css = "mat-checkbox.apply-location-checkbox div.mdc-checkbox")
    private WebElement applyLocationToAllDaysCheckbox;

    @FindBy(css = "mat-checkbox.apply-link-checkbox div.mdc-checkbox")
    private WebElement applyLinkToAllDaysCheckbox;

    @FindBy(css = "input[type='file']")
    private WebElement uploadImageInput;

    @FindBy(css = "div.stock-images img")
    private List<WebElement> stockImages;

    @FindBy(css = "button.tertiary-global-button")
    private WebElement cancelButton;

    @FindBy(css = "button.secondary-global-button.submit-buttons")
    private WebElement previewButton;

    @FindBy(css = "button.primary-global-button.submit-buttons")
    private WebElement publishButton;


    public CreateEventPage setTitle(String title) {
        typeText(titleInput, title);
        return this;
    }

    public CreateEventPage selectDuration(String value) {
        return selectDropdownOption(durationSelect, value);
    }

    public CreateEventPage selectInitiativeType(String type) {
        waitUntilAllElementsVisible(typeChips);
        WebElement chip = typeChips.stream()
                .filter(c -> c.getText().trim().equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown initiative type: " + type
                ));

        clickElement(chip);
        return this;
    }

    public CreateEventPage selectEventType(String value) {
        return selectDropdownOption(eventTypeDropdown, value);
    }

    public CreateEventPage invite(String value) {
        return selectDropdownOption(inviteDropdown, value);
    }

    public CreateEventPage setDescription(String description) {
        typeText(descriptionEditor, description);
        return this;
    }

    public CreateEventPage openDatePicker() {
        clickElement(dateInput);
        return this;
    }

    public CreateEventPage goToNextMonth() {
        clickElement(nextMonthButton);
        return this;
    }

    public CreateEventPage goToPreviousMonth() {
        clickElement(previousMonthButton);
        return this;
    }

    public CreateEventPage selectDay(int day) {
        waitUntilAllElementsVisible(calendarDayCells);
        WebElement dayCell = calendarDayCells.stream()
                .filter(cell -> cell.getText().trim().equals(String.valueOf(day)))
                .filter(cell -> !isDisabledCalendarCell(cell))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Day " + day + " not found or is disabled in the visible calendar month"
                        )
                );

        clickElement(dayCell);
        return this;
    }

    private boolean isDisabledCalendarCell(WebElement cell) {
        Boolean disabled = (Boolean) js.executeScript(
                "return arguments[0].closest('.mat-calendar-body-disabled') !== null"
                        + " || arguments[0].closest('[aria-disabled=\"true\"]') !== null;",
                cell);
        return Boolean.TRUE.equals(disabled);
    }

    public CreateEventPage setStartTime(String start) {
        typeText(startTimeInput, start);
        return this;
    }

    public CreateEventPage setEndTime(String end) {
        typeText(endTimeInput, end);
        return this;
    }

    public CreateEventPage toggleAllDay() {
        clickElement(allDayCheckbox);
        return this;
    }

    public CreateEventPage togglePlace() {
        clickElement(placeCheckbox);
        return this;
    }

    public CreateEventPage toggleOnline() {
        clickElement(onlineCheckbox);
        return this;
    }

    public CreateEventPage setLocation(String location) {
        typeText(locationInput, location);
        return this;
    }

    public CreateEventPage setOnlineLink(String link) {
        typeText(onlineLinkInput, link);
        return this;
    }

    public CreateEventPage applyLocationToAllDays() {
        clickElement(applyLocationToAllDaysCheckbox);
        return this;
    }

    public CreateEventPage applyLinkToAllDays() {
        clickElement(applyLinkToAllDaysCheckbox);
        return this;
    }

    public CreateEventPage uploadImage(String absoluteFilePath) {
        uploadImageInput.sendKeys(absoluteFilePath);
        return this;
    }

    public CreateEventPage selectStockImage(int index) {
        clickElement(stockImages.get(index));
        return this;
    }

    public EventsPage cancel() {
        clickElement(cancelButton);
        return new EventsPage(driver);
    }

    public CreateEventPage preview() {
        clickElement(previewButton);
        return this;
    }

    public EventDetailsPage publish() {
        clickElement(publishButton);
        return new EventDetailsPage(driver);
    }


    private CreateEventPage selectDropdownOption(
            WebElement dropdown,
            String value
    ) {
        clickElement(dropdown);

        WebElement option = driver.findElement(
                By.xpath("//mat-option[normalize-space(.)=" + xpathLiteral(value) + "]")
        );

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
