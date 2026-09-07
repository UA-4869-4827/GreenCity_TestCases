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
        initPageElements();
        waitUntilElementVisible(menuPanel);
    }

    public PlacesPage selectOption(String optionText) {
        clickBy(By.xpath("//div[contains(@class,'mat-mdc-menu-panel') or contains(@class,'mat-menu-panel')]"
                + "//button[contains(normalize-space(.), " + xpathLiteral(optionText) + ")]"));
        return new PlacesPage(driver);
    }
}
