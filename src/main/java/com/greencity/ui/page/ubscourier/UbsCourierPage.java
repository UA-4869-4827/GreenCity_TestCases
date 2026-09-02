package com.greencity.ui.page.ubscourier;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UbsCourierPage extends BasePage {

    private static final String GREEN_CITY_UBSCOURIER_HASH = "/#/ubs";

    public UbsCourierPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.urlContains(GREEN_CITY_UBSCOURIER_HASH));
        waitForPageToLoad(10);
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(GREEN_CITY_UBSCOURIER_HASH);
    }
}
