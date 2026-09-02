package com.greencity.ui.page.friends;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class FriendsPage extends BasePage {

    public FriendsPage (WebDriver driver){
        super(driver);
    }

    public boolean isOpened() {
        return Objects.requireNonNull(driver.getCurrentUrl()).contains("/friends");
    }
}
