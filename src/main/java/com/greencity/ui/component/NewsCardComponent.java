package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.NewsDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsCardComponent extends BaseComponent {

    @FindBy(xpath = ".//span[contains(@class,'flag')]")
    private WebElement bookmarkButton;

    public NewsCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public NewsDetailsPage openNews() {
        clickElement(rootElement);
        return new NewsDetailsPage(driver);
    }

    public SignInModal bookmarkNews() {
        clickElement(bookmarkButton);
        return new SignInModal(driver);
    }
}
