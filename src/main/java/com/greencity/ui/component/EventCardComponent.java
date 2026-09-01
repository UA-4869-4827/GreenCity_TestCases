package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.events.CreateEventPage;
import com.greencity.ui.page.events.EventDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventCardComponent extends BaseComponent {

    @FindBy(css = "p.event-name")
    private WebElement title;

    @FindBy(css = "button.secondary-global-button")
    private WebElement moreButton;

    @FindBy(css = "button.event-button")
    private WebElement joinButton;

    @FindBy(xpath = ".//button[normalize-space()='Edit event']")
    private WebElement editButton;


    @FindBy(css = "button.like")
    private WebElement likeButton;

    @FindBy(css = "button.dislike")
    private WebElement dislikeButton;

    @FindBy(css = "span.flag")
    private WebElement bookmarkButton;

    @FindBy(xpath = ".//div[contains(@class,'date-container')][.//span[contains(@class,'place')]]/p")

    private WebElement locationText;

    @FindBy(css = "div.date-container div.date")
    private WebElement dateText;

    @FindBy(css = "div.author p")
    private WebElement authorText;


    public EventCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }


    public String getTitle() {
        return getElementText(title);
    }

    public String getLocation() {
        return isElementDisplayed(locationText) ? getElementText(locationText) : "";
    }

    public String getDate() {
        return getElementText(dateText);
    }

    public String getAuthor() {
        return getElementText(authorText);
    }


    public boolean isOwnEvent() {
        return isElementDisplayed(editButton);
    }

    public EventDetailsPage openEventDetails() {
        clickElement(moreButton);
        return new EventDetailsPage(driver);
    }


    public CreateEventPage editEvent() {
        clickElement(editButton);
        return new CreateEventPage(driver);
    }


    public EventCardComponent joinEvent() {
        clickElement(joinButton);
        return this;
    }

    public SignInModal joinEventAsGuest() {
        clickElement(joinButton);
        return new SignInModal(driver);
    }


    public EventCardComponent likeEvent() {
        clickElement(likeButton);
        return this;
    }

    public SignInModal likeEventAsGuest() {
        clickElement(likeButton);
        return new SignInModal(driver);
    }


    public EventCardComponent dislikeEvent() {
        clickElement(dislikeButton);
        return this;
    }


    public EventCardComponent bookmarkEvent() {
        clickElement(bookmarkButton);
        return this;
    }


    public SignInModal bookmarkEventAsGuest() {
        clickElement(bookmarkButton);
        return new SignInModal(driver);
    }
}