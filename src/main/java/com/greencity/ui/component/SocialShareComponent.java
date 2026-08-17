package com.greencity.ui.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SocialShareComponent extends BaseComponent {

    public SocialShareComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @FindBy(xpath = ".//img[@alt='Share on Twitter']")
    private WebElement twitterButton;

    @FindBy(xpath = ".//img[@alt='Share on Linkedin']")
    private WebElement linkedInButton;

    @FindBy(xpath = ".//img[@alt='Share on Facebook']")
    private WebElement facebookButton;

    public void shareOnTwitter() {
        twitterButton.click();
    }

    public void shareOnLinkedIn() {
        linkedInButton.click();
    }

    public void shareOnFacebook() {
        facebookButton.click();
    }
}