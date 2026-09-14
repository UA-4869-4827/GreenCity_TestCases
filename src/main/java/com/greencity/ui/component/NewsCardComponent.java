package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.NewsDetailsPage;

import java.util.List;

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

    @FindBy(css = ".filter-tag span:not(.tag-divider)")
    private List<WebElement> tags;

    @FindBy(xpath = ".//p[img[@alt='likes']]/span[@class='numerosity']")
    private WebElement likesCounter;

    @FindBy(xpath = ".//p[img[@alt='comments']]/span[@class='numerosity']")
    private WebElement commentsCounter;

    public NewsCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public String getTitle() {
        return getElementText(title);
    }

    public String getContent() {
        return getElementText(content);
    }

    public String getDate() {
        return getElementText(date);
    }

    public String getAuthor() {
        return getElementText(author);
    }

    public List<String> getTags() {
        return tags.stream()
                .map(element -> element.getText())
                .toList();
    }

    public String getLikesCount() {
        return getElementText(likesCounter);
    }

    public String getCommentsCount() {
        return getElementText(commentsCounter);
    }

    public NewsDetailsPage openNews() {
        clickElement(title);
        return new NewsDetailsPage(driver);
    }

    public NewsCardComponent bookmarkNews() {
        clickElement(bookmarkButton);
        return this;
    }

    public SignInModal bookmarkNewsAsGuest() {
        clickElement(bookmarkButton);
        return new SignInModal(driver);
    }

}
