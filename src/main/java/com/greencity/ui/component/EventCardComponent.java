package com.greencity.ui.page.events;

import com.greencity.ui.component.BaseComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EventCardComponent extends BaseComponent {

    private final WebElement root;
    private final WebDriver driver;

    public EventCardComponent(WebElement root, WebDriver driver) {
        this.root = root;
        this.driver = driver;
        PageFactory.initElements(root, this);
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
        moreButton.click();
        return new EventDetailsPage(driver);
    }

    public void joinEvent() {
        joinButton.click();
    }

    public void likeEvent() {
        likeButton.click();
    }

    public void dislikeEvent() {
        dislikeButton.click();
    }

    public void bookmarkEvent() {
        bookmarkButton.click();
    }
}