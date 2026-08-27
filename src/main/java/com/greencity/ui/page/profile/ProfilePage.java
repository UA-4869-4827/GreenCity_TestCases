package com.greencity.ui.page.profile;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProfilePage extends BasePage {

    public ProfilePage(WebDriver driver){super(driver);}

    private static final String GREEN_CITY_PROFILE_HASH = "/#/greenCity/profile";

    public ProfilePage waitUntilPageLoaded() {
        wait.until(ExpectedConditions.urlContains(GREEN_CITY_PROFILE_HASH));
        waitForPageToLoad(10);
        return this;
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(GREEN_CITY_PROFILE_HASH);
    }
}
