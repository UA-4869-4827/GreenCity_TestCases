package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.NewsDetailsPage;
import org.openqa.selenium.By;
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

    @FindBy(css = "img.list-image-content, img.eco-news-list-view-img")
    private WebElement cardImage;

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

    public String getTags() {
        return getElementText(tags);
    }

    public String getLikesCount() {
        return getElementText(likesCounter);
    }

    public String getCommentsCount() {
        return getElementText(commentsCounter);
    }

    public boolean isImageDisplayed() {
        return isElementDisplayed(cardImage);
    }

    /**
     * Bookmark uses CSS :hover — trigger hover via JS events to reveal it.
     */
    public boolean isBookmarkDisplayed() {
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('mouseenter', {bubbles:true}));",
            rootElement);
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles:true}));",
            rootElement);
        return !rootElement.findElements(By.xpath(".//span[contains(@class,'flag')]")).isEmpty();
    }

    public boolean isTitleDisplayed() {
        return isElementDisplayed(title);
    }

    public boolean isAuthorDisplayed() {
        return isElementDisplayed(author);
    }

    public boolean isDateDisplayed() {
        return isElementDisplayed(date);
    }

    public boolean isLikesCounterDisplayed() {
        return isElementDisplayed(likesCounter);
    }

    public boolean isCommentsCounterDisplayed() {
        return isElementDisplayed(commentsCounter);
    }

    public boolean isTagDisplayed() {
        return isElementDisplayed(tags);
    }

    public NewsDetailsPage openNews() {
        clickElement(title);
        return new NewsDetailsPage(driver);
    }

    public NewsCardComponent bookmarkNews() {
        triggerHover();
        clickElementWithJs(bookmarkButton);
        return this;
    }

    public SignInModal bookmarkNewsAsGuest() {
        triggerHover();
        waitUntilElementClickable(bookmarkButton);
        clickElementWithJs(bookmarkButton);
        return new SignInModal(driver);
    }

    private void triggerHover() {
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('mouseenter', {bubbles:true}));",
            rootElement);
        js.executeScript(
            "arguments[0].dispatchEvent(new MouseEvent('mouseover', {bubbles:true}));",
            rootElement);
    }
}
