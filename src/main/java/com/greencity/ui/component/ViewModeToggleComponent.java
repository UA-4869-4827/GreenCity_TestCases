package com.greencity.ui.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewModeToggleComponent extends BaseComponent {

    @FindBy(xpath = ".//span[@aria-label='table view']")
    private WebElement tableViewButton;

    @FindBy(xpath = ".//span[@aria-label='list view']")
    private WebElement listViewButton;

    public ViewModeToggleComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public ViewModeToggleComponent switchToTableView() {
        clickElement(tableViewButton);
        return this;
    }

    public ViewModeToggleComponent switchToListView() {
        clickElement(listViewButton);
        return this;
    }
}
