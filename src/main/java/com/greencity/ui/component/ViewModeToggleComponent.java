package com.greencity.ui.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewModeToggleComponent extends BaseComponent {

    public ViewModeToggleComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @FindBy(xpath = ".//*[contains(@class, 'gallery')]")
    private WebElement tableViewButton;

    @FindBy(xpath = ".//*[contains(@class, 'list')]")
    private WebElement listViewButton;

    public void switchToTableView() {
        tableViewButton.click();
    }

    public void switchToListView() {
        listViewButton.click();
    }
}