package com.greencity.ui.component;

import com.greencity.ui.elements.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewModeToggleComponent extends BaseElement {

    public ViewModeToggleComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @FindBy(xpath = ".//*[contains(@class, 'gallery')]")
    private WebElement tableViewButton;

    @FindBy(xpath = ".//*[contains(@class, 'list')]")
    private WebElement listViewButton;

    public void switchToTableView() {
        waitUntilElementClickable(tableViewButton);
        tableViewButton.click();
    }

    public void switchToListView() {
        waitUntilElementClickable(listViewButton);
        listViewButton.click();
    }
}
