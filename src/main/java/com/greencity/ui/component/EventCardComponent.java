package com.greencity.ui.page.events;

import com.greencity.ui.elements.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventCardComponent extends BaseElement {

    private final WebDriver driver;

    public EventCardComponent(WebElement root, WebDriver driver) {
        super(driver, root);
        this.driver = driver;
    }

    @FindBy(xpath = ".//button[contains(text(),'Більше')]")
    private WebElement moreButton;

    @FindBy(xpath = ".//button[contains(text(),'Приєднатися до події')]")
    private WebElement joinButton;

    @FindBy(xpath = ".//button[contains(@class, 'like')]")
    private WebElement likeButton;

    @FindBy(xpath = ".//button[contains(@class, 'dislike')]")
    private WebElement dislikeButton;

    @FindBy(xpath = ".//button[contains(@class, 'flaf')]")
    private WebElement bookmarkButton;

    public EventDetailsPage openEventDetails() {
        waitUntilElementClickable(moreButton);
        moreButton.click();
        return new EventDetailsPage(driver);
    }

    public void joinEvent() {
        waitUntilElementClickable(joinButton);
        joinButton.click();
    }

    public void likeEvent() {
        waitUntilElementClickable(likeButton);
        likeButton.click();
    }

    public void dislikeEvent() {
        waitUntilElementClickable(dislikeButton);
        dislikeButton.click();
    }

    public void bookmarkEvent() {
        waitUntilElementClickable(bookmarkButton);
        bookmarkButton.click();
    }
}
