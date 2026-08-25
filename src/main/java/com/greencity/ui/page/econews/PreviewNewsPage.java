package com.greencity.ui.page.econews;

import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.EcoNews.EcoNewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PreviewNewsPage extends BasePage {

    private By backToEditingLink = By.cssSelector("div.back-button");
    private By editButton = By.cssSelector("button.primary-global-button");

    public PreviewNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage clickBackToEditing() {
        click(backToEditingLink);
        return new CreateNewsPage(driver);
    }

    public EcoNewsPage clickEdit() {
        click(editButton);
        return new EcoNewsPage(driver);
    }
}

