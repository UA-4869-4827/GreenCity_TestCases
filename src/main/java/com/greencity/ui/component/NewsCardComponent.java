package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.econews.NewsDetailsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
<<<<<<< HEAD
import org.openqa.selenium.support.FindBy;
=======
>>>>>>> 412c350 (same fixes)

public class NewsCardComponent extends BaseComponent {

<<<<<<< HEAD
    @FindBy(xpath = ".//span[contains(@class,'flag')]")
    private WebElement bookmarkButton;

    public NewsCardComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public NewsDetailsPage openNews() {
        clickElement(rootElement);
=======
    private WebElement root;

    private By title = By.cssSelector(".title-list h3");
    private By content = By.cssSelector(".list-text p");
    private By date = By.cssSelector("p.text-nowrap span");
    private By author = By.cssSelector("span.mw");
    private By tags = By.cssSelector(".filter-tag span");
    private By likesCounter = By.xpath(".//p[img[@alt='likes']]/span[@class='numerosity']");
    private By commentsCounter = By.xpath(".//p[img[@alt='comments']]/span[@class='numerosity']");
    private By bookmarkButton = By.cssSelector("span.flag");

    public NewsCardComponent(WebDriver driver, WebElement root) {
        super(driver);
        this.root = root;
    }

    public NewsDetailsPage clickTitle() {
        root.findElement(title).click();
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickContent() {
        root.findElement(content).click();
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickDate() {
        root.findElement(date).click();
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickAuthor() {
        root.findElement(author).click();
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickTags() {
        root.findElement(tags).click();
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickLikesCounter() {
        root.findElement(likesCounter).click();
        return new NewsDetailsPage(driver);
    }

    public NewsDetailsPage clickCommentsCounter() {
        root.findElement(commentsCounter).click();
>>>>>>> 412c350 (same fixes)
        return new NewsDetailsPage(driver);
    }

    public SignInModal bookmarkNews() {
<<<<<<< HEAD
        clickElement(bookmarkButton);
=======
        root.findElement(bookmarkButton).click();
>>>>>>> 412c350 (same fixes)
        return new SignInModal(driver);
    }
}