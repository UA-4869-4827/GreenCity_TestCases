package com.greencity.ui.page.events;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EventPreviewPage extends BasePage {

    @FindBy(css = "a.button-link")
    private WebElement backToEditingLink;

    @FindBy(css = "button.button-publish")
    private WebElement publishButton;

    public EventPreviewPage(WebDriver driver) {
        super(driver);
    }

    public CreateEventPage clickBackToEditing() {
        clickElement(backToEditingLink);
        return new CreateEventPage(driver);
    }

    public EventsPage publish() {
        clickElement(publishButton);
        return new EventsPage(driver);
    }
}
