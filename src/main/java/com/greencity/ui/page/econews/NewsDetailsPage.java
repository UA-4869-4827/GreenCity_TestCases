package com.greencity.ui.page.econews;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.component.SocialShareComponent;
import com.greencity.ui.page.BasePage;
import lombok.Getter;
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

    @FindBy(css = "app-comments-container")
    private WebElement commentsRoot;

    @FindBy(css = "div.edit-news")
    private WebElement editNewsButton;

    @FindBy(css = "button.delete-news-button")
    private WebElement deleteButton;

    @FindBy(css = "app-warning-pop-up button.primary-global-button")
    private WebElement confirmDeleteButton;

    @Getter
    private final CommentsComponent comments;
    @Getter
    private final SocialShareComponent socialShare;

    public NewsDetailsPage(WebDriver driver) {
        super(driver);
        this.socialShare = new SocialShareComponent(driver, socialShareRoot);
        this.comments = new CommentsComponent(driver, commentsRoot);
    }

    public NewsDetailsPage likeArticle() {
        clickElement(likeButton);
        return this;
    }

    public EcoNewsPage goBackToNews() {
        clickElement(backToNewsButton);
        return new EcoNewsPage(driver);
    }

    public CreateNewsPage editNews() {
        clickElement(editNewsButton);
        return new CreateNewsPage(driver);
    }

    public EcoNewsPage deleteNews() {
        clickElement(deleteButton);
        clickElement(confirmDeleteButton);
        return new EcoNewsPage(driver);
    }

    public NewsDetailsPage openRelatedNews(int index) {
        clickElement(getVisibleItem(relatedNewsList, index));
        return new NewsDetailsPage(driver);
    }
}
