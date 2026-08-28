package com.greencity.ui.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SocialShareComponent extends BaseComponent {

    @FindBy(xpath = ".//img[@alt='facebook' or @alt='Share on Facebook']")
    private WebElement facebookButton;

    @FindBy(xpath = ".//img[@alt='linkedin' or @alt='Share on Linkedin']")
    private WebElement linkedinButton;

    @FindBy(xpath = ".//img[@alt='twitter' or @alt='Share on Twitter']")
    private WebElement twitterButton;

    public SocialShareComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public void shareToTwitter() {
        clickElement(twitterButton);
    }

    public void shareToLinkedIn() {
        clickElement(linkedinButton);
    }

    public void shareToFacebook() {
        clickElement(facebookButton);
    }
}
