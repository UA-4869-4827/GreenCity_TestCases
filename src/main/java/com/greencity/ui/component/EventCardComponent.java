package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
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

    @FindBy(css = "button.like")
    private WebElement likeButton;

    @FindBy(css = "button.dislike")
    private WebElement dislikeButton;

    @FindBy(css = "span.flag")
    private WebElement bookmarkButton;

    public EventCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getTitle() {
        return getElementText(title);
    }

    public EventDetailsPage openEventDetails() {
        clickElement(moreButton);
        return new EventDetailsPage(driver);
    }

    public SignInModal joinEvent() {
        clickElement(joinButton);
        return new SignInModal(driver);
    }

    public SignInModal likeEvent() {
        clickElement(likeButton);
        return new SignInModal(driver);
    }

    public EventCardComponent dislikeEvent() {
        clickElement(dislikeButton);
        return this;
    }

    public SignInModal bookmarkEvent() {
        clickElement(bookmarkButton);
        return new SignInModal(driver);
    }
}
