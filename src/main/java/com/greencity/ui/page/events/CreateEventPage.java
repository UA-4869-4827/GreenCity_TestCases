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

    @FindBy(css = "input[placeholder='Введіть назву події']")
    private WebElement titleInput;

    @FindBy(css = "mat-select[formcontrolname='duration']")
    private WebElement durationSelect;

    @FindBy(xpath = "//mat-chip-option[.//span[contains(@class,'text-label')][normalize-space()='Економічний']]")
    private WebElement economicTypeChip;

    @FindBy(xpath = "//mat-chip-option[.//span[contains(@class,'text-label')][normalize-space()='Соціальний']]")
    private WebElement socialTypeChip;

    @FindBy(xpath = "//mat-chip-option[.//span[contains(@class,'text-label')][normalize-space()='Екологічний']]")
    private WebElement ecologicalTypeChip;

    @FindBy(css = "mat-select[formcontrolname='open']")
    private WebElement eventTypeDropdown;

    @FindBy(xpath = "//mat-label[normalize-space()='Запросити']/ancestor::mat-form-field//mat-select")
    private WebElement inviteDropdown;

    @FindBy(css = "div.ql-editor")
    private WebElement descriptionEditor;

    @FindBy(xpath = "//*[contains(text(),'Обрати дату')]")
    private WebElement dateButton;

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

    @FindBy(css = "mat-checkbox[formcontrolname='allDay'] input")
    private WebElement allDayCheckbox;

    @FindBy(xpath = "//mat-checkbox[.//label[normalize-space()='Місце']]//input")
    private WebElement placeCheckbox;

    @FindBy(xpath = "//mat-checkbox[.//label[normalize-space()='Онлайн']]//input")
    private WebElement onlineCheckbox;

    @FindBy(css = "input[placeholder='Місце проведення події*']")
    private WebElement locationInput;

    @FindBy(css = "input[placeholder*='посилання на подію']")
    private WebElement onlineLinkInput;

    @FindBy(xpath = "(//*[normalize-space(text())='Застосувати на всі дні заходу']/preceding-sibling::input)[1]")
    private WebElement applyLocationToAllDaysCheckbox;

    @FindBy(xpath = "(//*[normalize-space(text())='Застосувати на всі дні заходу']/preceding-sibling::input)[2]")
    private WebElement applyLinkToAllDaysCheckbox;

    @FindBy(css = "input[type='file']")
    private WebElement uploadImageInput;

    @FindBy(css = "div.stock-images img")
    private List<WebElement> stockImages;

    @FindBy(xpath = "//*[normalize-space(text())='Відмінити']")
    private WebElement cancelButton;

    @FindBy(xpath = "//*[normalize-space(text())='Переглянути']")
    private WebElement previewButton;

    @FindBy(xpath = "//*[normalize-space(text())='Публікувати']")
    private WebElement publishButton;

    public CreateEventPage setTitle(String title) {
        typeText(titleInput, title);
        return this;
    }

    public CreateEventPage selectDuration(String value) {
        return selectDropdownOption(durationSelect, value);
    }

    public CreateEventPage selectInitiativeType(String type) {
        WebElement chip = switch (type) {
            case "Економічний" -> economicTypeChip;
            case "Соціальний" -> socialTypeChip;
            case "Екологічний" -> ecologicalTypeChip;
            default -> throw new IllegalArgumentException("Unknown initiative type: " + type);
        };
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
        clickElement(descriptionEditor);
        descriptionEditor.sendKeys(description);
        return this;
    }

    public CreateEventPage openDatePicker() {
        clickElement(dateButton);
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
        WebElement dayCell = calendarDayCells.stream()
                .filter(cell -> cell.getText().trim().equals(String.valueOf(day)))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Day " + day + " not found in visible calendar month"));
        clickElement(dayCell);
        return this;
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

    private CreateEventPage selectDropdownOption(WebElement dropdown, String value) {
        clickElement(dropdown);
        WebElement option = driver.findElement(
                By.xpath("//mat-option[contains(normalize-space(.), '" + value + "')]"));
        clickElement(option);
        return this;
    }
}