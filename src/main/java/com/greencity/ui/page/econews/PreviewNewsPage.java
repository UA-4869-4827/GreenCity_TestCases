package com.greencity.ui.page.econews;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PreviewNewsPage extends BasePage {

    @FindBy(css = "div.back-button")
    private WebElement backToEditingLink;

    @FindBy (css = "button.primary-global-button")
    private WebElement editButton;


    public PreviewNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage clickBackToEditing() {
        clickElement(backToEditingLink);
        return new CreateNewsPage(driver);
    }

    public EcoNewsPage clickEdit() {
        clickElement(editButton);
        return new EcoNewsPage(driver);
    }
}

