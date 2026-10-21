package com.greencity.ui.page.econews;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.BasePage;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PreviewNewsPage extends BasePage {

    @FindBy(css = "div.back-button")
    private WebElement backToEditingLink;

    @FindBy(css = "app-news-preview-page .create-news-text")
    private WebElement pageHeading;

    @FindBy(css = "app-news-preview-page .tags-item")
    private List<WebElement> tags;

    @FindBy(css = "app-news-preview-page .news-title")
    private WebElement title;

    @FindBy(css = "app-news-preview-page .news-text-content")
    private WebElement content;

    @FindBy(css = "app-news-preview-page .news-info-author")
    private WebElement author;

    @FindBy(css = "app-news-preview-page form.submit-form button[type='submit']")
    private WebElement publishButton;

    public PreviewNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage clickBackToEditing() {
        clickElement(backToEditingLink);
        return new CreateNewsPage(driver);
    }

    public boolean isOpened() {
        return isElementDisplayed(pageHeading);
    }

    public String getTitleText() {
        return getElementText(title);
    }

    public String getContentText() {
        return getElementText(content);
    }

    public String getAuthorText() {
        return getElementText(author);
    }

    public List<String> getTagTexts() {
        waitUntilAllElementsVisible(tags);
        return tags.stream()
                .map(this::getElementText)
                .toList();
    }

    @Step("Publish news from preview")
    public EcoNewsPage publish() {
        clickElement(publishButton);
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.urlMatches(".*/greenCity/news/?$"));
        waitForPageToLoad();
        return new EcoNewsPage(driver);
    }

    public CreateNewsPage edit() {
        clickBy(By.xpath(
                "//button[contains(@class,'primary-global-button') and normalize-space()="
                        + xpathLiteral(UiMessage.CREATE_NEWS_EDIT.text()) + "]"));
        return new CreateNewsPage(driver);
    }
}
