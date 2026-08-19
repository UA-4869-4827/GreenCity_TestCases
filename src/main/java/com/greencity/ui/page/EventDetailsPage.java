package com.greencity.ui.page;

import com.greencity.ui.page.modal.SignInModal;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventDetailsPage extends BasePage {

    public EventDetailsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//img[@class='event-like']")
    private WebElement likeButton;

    @FindBy(xpath = "//button[contains(text(), 'Зберегти подію')]")
    private WebElement saveEventButton;

    @FindBy(xpath = "//button[contains(text(), 'Приєднатись до події')]")
    private WebElement joinButton;

    @FindBy(xpath = "//div[contains(text(), 'Повернутися')]/ancestor::button")
    private WebElement backButton;


    public void likeEvent() {
        waitUntilElementClickable(likeButton);
        likeButton.click();
    }

    public SignInModal saveEvent() {
        waitUntilElementClickable(saveEventButton);
        saveEventButton.click();
        return new SignInModal(driver);
    }

    public SignInModal joinEvent() {
        waitUntilElementClickable(joinButton);
        joinButton.click();
        return new SignInModal(driver);
    }

    public EventsPage goBackToEvents() {
        waitUntilElementClickable(backButton);
        backButton.click();
        return new EventsPage(driver);
    }
}