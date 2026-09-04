package com.greencity.ui.page.ubscourier;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;

public class UbsCourierPage extends BasePage {

    private static final String GREEN_CITY_UBSCOURIER_HASH = "/#/ubs";

    public UbsCourierPage(WebDriver driver) {
        super(driver);
        waitUntilUrlContains(GREEN_CITY_UBSCOURIER_HASH);
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(GREEN_CITY_UBSCOURIER_HASH);
    }
}
