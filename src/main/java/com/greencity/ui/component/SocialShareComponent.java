package com.greencity.ui.component;

import com.greencity.ui.elements.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SocialShareComponent extends BaseElement {

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
        waitUntilElementClickable(twitterButton);
        twitterButton.click();
    }

    public void shareOnLinkedIn() {
        waitUntilElementClickable(linkedInButton);
        linkedInButton.click();
    }

    public void shareOnFacebook() {
        waitUntilElementClickable(facebookButton);
        facebookButton.click();
    }
}
