package com.greencity.ui.page.profile;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;

public class ProfilePage extends BasePage {

    private static final String GREEN_CITY_PROFILE_HASH = "/#/greenCity/profile";

    public ProfilePage(WebDriver driver) {
        super(driver);
        waitUntilUrlContains(GREEN_CITY_PROFILE_HASH);
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(GREEN_CITY_PROFILE_HASH);
    }
}
