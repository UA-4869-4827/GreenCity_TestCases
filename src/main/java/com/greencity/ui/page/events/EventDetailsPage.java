package com.greencity.ui.page.events;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventDetailsPage extends BasePage {

    @FindBy(css = "img.event-like")
    private WebElement likeButton;

    @FindBy(css = "a.button-link")
    private WebElement backToEventsButton;

    @FindBy(css = "app-comments-container")
    private WebElement commentsRoot;

    @Getter
    private final CommentsComponent comments;

    public EventDetailsPage(WebDriver driver) {
        super(driver);
        this.comments = new CommentsComponent(driver, commentsRoot);
    }

    public EventDetailsPage likeEvent() {
        clickElement(likeButton);
        return this;
    }

    public boolean isLikeDisabled() {
        return getElementAttribute(likeButton, "class").contains("disable");
    }

    public boolean isEditDisplayed() {
        return isElementDisplayed(byNormalizedText("//button", UiMessage.EVENT_DETAILS_EDIT));
    }

    public EventDetailsPage saveEvent() {
        clickBy(byNormalizedText("//button", UiMessage.EVENT_DETAILS_SAVE));
        return this;
    }

    public SignInModal saveEventAsGuest() {
        clickBy(byNormalizedText("//button", UiMessage.EVENT_DETAILS_SAVE));
        return new SignInModal(driver);
    }

    public EventDetailsPage joinEvent() {
        clickBy(byNormalizedText("//button", UiMessage.EVENT_DETAILS_JOIN));
        return this;
    }

    public SignInModal joinEventAsGuest() {
        clickBy(byNormalizedText("//button", UiMessage.EVENT_DETAILS_JOIN));
        return new SignInModal(driver);
    }

    public CreateEventPage editEvent() {
        clickBy(By.xpath("//button[contains(@class,'secondary-global-button') and normalize-space()="
                + xpathLiteral(UiMessage.EVENT_DETAILS_EDIT.text()) + "]"));
        return new CreateEventPage(driver);
    }

    public EventsPage goBackToEvents() {
        clickElement(backToEventsButton);
        return new EventsPage(driver);
    }
}
