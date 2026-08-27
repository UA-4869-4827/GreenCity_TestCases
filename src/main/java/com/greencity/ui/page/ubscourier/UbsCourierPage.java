package com.greencity.ui.page.ubscourier;

import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.profile.ProfilePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UbsCourierPage extends BasePage {

    public UbsCourierPage(WebDriver driver){super(driver);}

    private static final String GREEN_CITY_UBSCOURIER_HASH = "/#/ubs";

    public UbsCourierPage waitUntilPageLoaded() {
        wait.until(ExpectedConditions.urlContains(GREEN_CITY_UBSCOURIER_HASH));
        waitForPageToLoad(10);
        return this;
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(GREEN_CITY_UBSCOURIER_HASH);
    }
}

