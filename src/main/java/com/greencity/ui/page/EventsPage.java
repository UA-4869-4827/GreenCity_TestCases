package com.greencity.ui.page;

import com.greencity.ui.page.modal.SignInModal;
import com.greencity.ui.page.events.EventDetailsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class EventsPage extends BasePage {

    public EventsPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
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
        eventTimeFilterDropdown.click();
        selectOption(value);
    }

    public void filterByLocation(String value) {
        locationFilterDropdown.click();
        selectOption(value);
    }

    public void filterByStatus(String value) {
        statusFilterDropdown.click();
        selectOption(value);
    }

    public void filterByType(String value) {
        typeFilterDropdown.click();
        selectOption(value);
    }

    public void filterByDateRange(String value) {
        dateRangeFilterDropdown.click();
        selectOption(value);
    }

    public void resetAllFilters() {
        resetFiltersButton.click();
    }

    public SignInModal createEvent() {
        createEventButton.click();
        return new SignInModal(driver);
    }

    public EventDetailsPage openEventByIndex(int index) {
        moreInfoButtons.get(index).click();
        return new EventDetailsPage(driver);
    }


    private void selectOption(String value) {
        driver.findElement(By.xpath("//mat-option[contains(text(), '" + value + "')]")).click();
    }
}