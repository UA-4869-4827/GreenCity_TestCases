package com.greencity.ui.page;

import com.greencity.ui.page.modal.SignInModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class EventsPage extends BasePage {

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//mat-label[text()='Час події']/following-sibling::mat-select")
    private WebElement eventTimeFilterDropdown;

    @FindBy(xpath = "//mat-label[text()='Де?']/following-sibling::mat-select")
    private WebElement locationFilterDropdown;

    @FindBy(xpath = "//mat-label[text()='Статус']/following-sibling::mat-select")
    private WebElement statusFilterDropdown;

    @FindBy(xpath = "//mat-label[text()='Тип події']/following-sibling::mat-select")
    private WebElement typeFilterDropdown;

    @FindBy(xpath = "//mat-label[text()='Дати']/following-sibling::mat-select")
    private WebElement dateRangeFilterDropdown;

    @FindBy(css = "button.reset")
    private WebElement resetFiltersButton;

    @FindBy(xpath = "//div[contains(@class, 'create')]/button")
    private WebElement createEventButton;

    @FindBy(xpath = "//button[contains(text(),'Більше')]")
    private List<WebElement> moreInfoButtons;


    public void filterByEventTime(String value) {
        waitUntilElementClickable(eventTimeFilterDropdown);
        eventTimeFilterDropdown.click();
        selectOption(value);
    }

    public void filterByLocation(String value) {
        waitUntilElementClickable(locationFilterDropdown);
        locationFilterDropdown.click();
        selectOption(value);
    }

    public void filterByStatus(String value) {
        waitUntilElementClickable(statusFilterDropdown);
        statusFilterDropdown.click();
        selectOption(value);
    }

    public void filterByType(String value) {
        waitUntilElementClickable(typeFilterDropdown);
        typeFilterDropdown.click();
        selectOption(value);
    }

    public void filterByDateRange(String value) {
        waitUntilElementClickable(dateRangeFilterDropdown);
        dateRangeFilterDropdown.click();
        selectOption(value);
    }

    public void resetAllFilters() {
        waitUntilElementClickable(resetFiltersButton);
        resetFiltersButton.click();
    }

    public SignInModal createEvent() {
        waitUntilElementClickable(createEventButton);
        createEventButton.click();
        return new SignInModal(driver);
    }

    public EventDetailsPage openEventByIndex(int index) {
        WebElement button = moreInfoButtons.get(index);
        waitUntilElementClickable(button);
        button.click();
        return new EventDetailsPage(driver);
    }


    private void selectOption(String value) {
        WebElement option = driver.findElement(By.xpath("//mat-option[contains(text(), '" + value + "')]"));
        waitUntilElementClickable(option);
        option.click();
    }
}