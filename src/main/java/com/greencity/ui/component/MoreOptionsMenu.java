package com.greencity.ui.component;

import com.greencity.ui.Base;
import com.greencity.ui.page.places.PlacesPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MoreOptionsMenu extends Base {

    @FindBy(css = "div.mat-mdc-menu-panel, div.mat-menu-panel")
    private WebElement menuPanel;

    public MoreOptionsMenu(WebDriver driver) {
        super(driver);
        waitUntilElementVisible(menuPanel);
    }

    public PlacesPage selectOption(String optionText) {
        WebElement option = menuPanel.findElement(
                By.xpath(".//button[contains(normalize-space(.), '" + optionText + "')]"));
        clickElement(option);
        return new PlacesPage(driver);
    }
}
