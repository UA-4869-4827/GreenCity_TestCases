package com.greencity.ui.component;

import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.modal.SignInModal;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewsCardComponent extends BasePage {

    private By newsCard = By.xpath(""); // TODO;
    private By bookmarkButton = By.xpath(""); // TODO;

    public NewsCardComponent(WebDriver driver) {
        super(driver);
    }

    public NewsDetailsPage openNews() {
        click(newsCard);
        return new NewsDetailsPage(driver);
    }

    public SignInModal bookmarkNews() {
        click(bookmarkButton);
        return new SignInModal(driver);
    }
}
