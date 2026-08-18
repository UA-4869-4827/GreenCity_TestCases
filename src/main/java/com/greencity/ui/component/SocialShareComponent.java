package com.greencity.ui.component;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SocialShareComponent extends BasePage {

private By facebookButton = By.cssSelector("img[alt='facebook']");
private By linkedinButton = By.cssSelector("img[alt='linkedin']");
private By twitterButton = By.cssSelector("img[alt='twitter']");

    public SocialShareComponent(WebDriver driver) {
        super(driver);
    }

    public void shareToTwitter() {
        click(twitterButton);
    }

    public void shareToLinkedIn() {
        click(linkedinButton);
    }

    public void shareToFacebook() {
        click(facebookButton);
    }
}