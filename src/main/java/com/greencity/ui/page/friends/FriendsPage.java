package com.greencity.ui.page.friends;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;

public class FriendsPage extends BasePage {

    private static final String FRIENDS_HASH = "/#/greenCity/friends";

    public FriendsPage(WebDriver driver) {
        super(driver);
        waitUntilUrlContains(FRIENDS_HASH);
    }

    public boolean isOpened() {
        return getCurrentUrl().contains(FRIENDS_HASH);
    }
}
