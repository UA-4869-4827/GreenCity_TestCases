package com.greencity.ui.page.econews;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PreviewNewsPage extends BasePage {

    @FindBy(css = "div.back-button")
    private WebElement backToEditingLink;

    public PreviewNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage clickBackToEditing() {
        clickElement(backToEditingLink);
        return new CreateNewsPage(driver);
    }

    public CreateNewsPage edit() {
        clickBy(By.xpath(
                "//button[contains(@class,'primary-global-button') and normalize-space()="
                        + xpathLiteral(UiMessage.CREATE_NEWS_EDIT.text()) + "]"));
        return new CreateNewsPage(driver);
    }
}
