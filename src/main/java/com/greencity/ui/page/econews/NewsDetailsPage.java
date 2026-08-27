package com.greencity.ui.page.econews;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.component.SocialShareComponent;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class NewsDetailsPage extends BasePage {

    @FindBy(css = "img.news_like")
    private WebElement likeButton;

    @FindBy(css = "div.button-text")
    private WebElement backToNewsButton;

    @FindBy(css = "app-news-list-gallery-view.recommended-item")
    private List<WebElement> relatedNewsList;

    @FindBy(xpath = "//img[@alt='facebook']/parent::*")
    private WebElement socialShareRoot;

    @FindBy(xpath = "//div[contains(@class,'comment-body-wrapper')]/ancestor::*[contains(@class,'comment')][last()]")
    private WebElement commentsRoot;

    @FindBy(css = "div.edit-news")
    private WebElement editNewsButton;

    @FindBy(css = ".secondary-global-button")
    private WebElement deleteButton;

    private CommentsComponent comments;
    private SocialShareComponent socialShare;

    public NewsDetailsPage(WebDriver driver) {
        super(driver);
        this.socialShare = new SocialShareComponent(driver, socialShareRoot);
        this.comments = new CommentsComponent(driver, commentsRoot);
    }

    public CommentsComponent getComments() {
        return comments;
    }

    public SocialShareComponent getSocialShare() {
        return socialShare;
    }

    public NewsDetailsPage likeArticle() {
        clickElement(likeButton);
        return this;
    }

    public EcoNewsPage goBackToNews() {
        clickElement(backToNewsButton);
        return new EcoNewsPage(driver);
    }

    public CreateNewsPage clickEditNews() {
        clickElement(editNewsButton);
        return new CreateNewsPage(driver);
    }

    public EcoNewsPage clickDelete() {
        clickElement(deleteButton);
        return new EcoNewsPage(driver);
    }

    public NewsDetailsPage openRelatedNews(int index) {
        waitUntilAllElementsVisible(relatedNewsList);
        clickElement(relatedNewsList.get(index));
        return new NewsDetailsPage(driver);
    }
}
