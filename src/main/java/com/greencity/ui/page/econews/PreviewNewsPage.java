package com.greencity.ui.page.econews;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PreviewNewsPage extends BasePage {

    @FindBy(css = "div.back-button")
    private WebElement backToEditingLink;

    @FindBy(xpath = "//button[contains(@class,'primary-global-button') and normalize-space()='Edit']")
    private WebElement editButton;

    public PreviewNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage clickBackToEditing() {
        clickElement(backToEditingLink);
        return new CreateNewsPage(driver);
    }

    public CreateNewsPage clickEdit() {
        clickElement(editButton);
        return new CreateNewsPage(driver);
    }
}
