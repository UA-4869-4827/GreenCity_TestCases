package com.greencity.ui.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ViewModeToggleComponent extends BaseComponent {

<<<<<<< HEAD
    @FindBy(xpath = ".//*[@aria-label='table view']")
    private WebElement tableViewButton;
=======
    private By tableViewButton = By.cssSelector("span[aria-label='table view']");
    private By listViewButton = By.cssSelector("span[aria-label='list view']");
>>>>>>> 412c350 (same fixes)

    @FindBy(xpath = ".//*[@aria-label='list view']")
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
