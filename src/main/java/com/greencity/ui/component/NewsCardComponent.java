package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.NewsDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NewsCardComponent extends BaseComponent {

    @FindBy(xpath = ".//span[contains(@class,'flag')]")
    private WebElement bookmarkButton;

    @FindBy(css = ".title-list h3")
    private WebElement title;

    @FindBy(css = ".list-text p")
    private WebElement content;

    @FindBy(css = "p.text-nowrap span")
    private WebElement date;

    @FindBy(css = "span.mw")
    private WebElement author;

    @FindBy(css = ".filter-tag span")
    private WebElement tags;

    @FindBy(xpath = ".//p[img[@alt='likes']]/span[@class='numerosity']")
    private WebElement likesCounter;

    @FindBy(xpath = ".//p[img[@alt='comments']]/span[@class='numerosity']")
    private WebElement commentsCounter;

    public NewsCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public NewsDetailsPage openNews() {
        clickElement(rootElement);
         return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickTitle() {
        clickElement(title);
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickContent() {
        clickElement(content);
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickDate() {
        clickElement(date);
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickAuthor() {
        clickElement(author);
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickTags() {
        clickElement(tags);
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickLikesCounter() {
        clickElement(likesCounter);
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickCommentsCounter() {
        clickElement(commentsCounter);
        return new NewsDetailsPage(driver);
    }

    public SignInModal bookmarkNews() {
        clickElement(bookmarkButton);
        return new SignInModal(driver);
    }
}
