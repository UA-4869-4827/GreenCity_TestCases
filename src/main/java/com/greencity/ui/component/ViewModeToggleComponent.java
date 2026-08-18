package com.greencity.ui.component;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewModeToggleComponent extends BasePage {

    private By tableViewButton = By.cssSelector("span[aria-label='table view']"); 
    private By listViewButton = By.cssSelector("span[aria-label='list view']");

    public ViewModeToggleComponent(WebDriver driver) {
        super(driver);
    }

    public ViewModeToggleComponent switchToTableView() {
        click(tableViewButton);
        return this;
    }

    public ViewModeToggleComponent switchToListView() {
        click(listViewButton);
        return this;
    }
}
