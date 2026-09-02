package com.greencity.ui.page.events;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventDetailsPage extends BasePage {

    @FindBy(css = "img.event-like")
    private WebElement likeButton;

    @FindBy(xpath = "//button[normalize-space()='Save event']")
    private WebElement saveEventButton;

    @FindBy(xpath = "//button[normalize-space()='Join event']")
    private WebElement joinEventButton;

    @FindBy(xpath = "//button[contains(@class,'secondary-global-button') and normalize-space()='Edit']")
    private WebElement editEventButton;

    @FindBy(css = "a.button-link")
    private WebElement backToEventsButton;

    @FindBy(css = "app-comments-container")
    private WebElement commentsRoot;

    public EventDetailsPage(WebDriver driver) {
        super(driver);
    }

    public CommentsComponent getComments() {
        return new CommentsComponent(driver, commentsRoot);
    }

    public EventDetailsPage likeEvent() {
        clickElement(likeButton);
        return this;
    }

    public EventDetailsPage saveEvent() {
        clickElement(saveEventButton);
        return this;
    }

    public SignInModal saveEventAsGuest() {
        clickElement(saveEventButton);
        return new SignInModal(driver);
    }

    public EventDetailsPage joinEvent() {
        clickElement(joinEventButton);
        return this;
    }

    public SignInModal joinEventAsGuest() {
        clickElement(joinEventButton);
        return new SignInModal(driver);
    }

    public CreateEventPage editEvent() {
        clickElement(editEventButton);
        return new CreateEventPage(driver);
    }

    public EventsPage goBackToEvents() {
        clickElement(backToEventsButton);
        return new EventsPage(driver);
    }
}
