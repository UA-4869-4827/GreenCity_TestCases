package com.greencity.ui.page.friends;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;

public class FriendsPage extends BasePage {

    public FriendsPage(WebDriver driver) {
        super(driver);
        waitUntilUrlContains("/friends");
    }

    public boolean isOpened() {
        return getCurrentUrl().contains("/friends");
    }
}
