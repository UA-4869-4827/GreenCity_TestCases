package com.greencity.ui.component;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ViewModeToggleComponent extends BasePage {

    private By tableViewButton = By.xpath(""); // TODO
    private By listViewButton = By.xpath(""); // TODO

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
