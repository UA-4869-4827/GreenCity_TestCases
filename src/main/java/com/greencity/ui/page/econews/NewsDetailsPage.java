package com.greencity.ui.page.econews;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.component.NewsCardComponent;
import com.greencity.ui.component.SocialShareComponent;
import com.greencity.ui.page.BasePage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;

public class NewsDetailsPage extends BasePage {

    private static final String NEWS_HASH = "/#/greenCity/news";
    private static final Pattern NEWS_ID = Pattern.compile("/news/(\\d+)");
    private static final By SIGN_IN_MODAL = By.cssSelector("app-sign-in");

    @FindBy(css = "img.news_like")
    private WebElement likeButton;

    @FindBy(css = "app-eco-news-detail .back-button a")
    private WebElement backToNewsButton;

    @FindBy(css = "app-eco-news-widget app-news-list-gallery-view")
    private List<WebElement> relatedNewsList;

    @FindBy(css = "div.news-links-images")
    private WebElement socialShareRoot;

    @FindBy(css = "app-comments-container")
    private WebElement commentsRoot;

    @FindBy(css = "div.edit-news")
    private WebElement editNewsButton;

    @FindBy(css = "button.delete-news-button")
    private WebElement deleteButton;

    @FindBy(css = "app-warning-pop-up button.primary-global-button")
    private WebElement confirmDeleteButton;

    @FindBy(css = "div.news-title")
    private WebElement title;

    @FindBy(css = "div.news-info-date")
    private WebElement date;

    @FindBy(css = "div.news-info-author")
    private WebElement author;

    @FindBy(css = "div.news-text-content")
    private WebElement body;

    @FindBy(css = "app-eco-news-widget div.wrapper")
    private WebElement relatedNewsRoot;

    @Getter
    private final CommentsComponent comments;
    @Getter
    private final SocialShareComponent socialShare;

    public NewsDetailsPage(WebDriver driver) {
        super(driver);
        this.socialShare = new SocialShareComponent(driver, socialShareRoot);
        this.comments = new CommentsComponent(driver, commentsRoot);
    }

    @Step("Open news article {newsId}")
    public NewsDetailsPage open(long newsId) {
        open(NEWS_HASH + "/" + newsId);
        waitUntilElementVisible(title);
        return this;
    }


    public long getNewsId() {
        String url = driver.getCurrentUrl();
        Matcher matcher = NEWS_ID.matcher(url);
        if (!matcher.find()) {
            throw new IllegalStateException("Not a news details URL: " + url);
        }
        return Long.parseLong(matcher.group(1));
    }

    public String getTitleText() {
        return getElementText(title);
    }

    public String getBodyText() {
        return getElementText(body);
    }

    public String getDateText() {
        return getElementText(date);
    }

    public String getAuthorText() {
        return getElementText(author);
    }

    public boolean isRelatedNewsDisplayed() {
        scrollToElementWithJs(relatedNewsRoot);
        return isElementDisplayed(relatedNewsRoot);
    }

    public int getRelatedNewsCount() {
        return relatedNewsList.size();
    }

    @Step("Click the article like")
    public NewsDetailsPage likeArticle() {
        clickElement(likeButton);
        return this;
    }

    public boolean isLikeDisabled() {
        return getElementAttribute(likeButton, "class").contains("disable");
    }

    public boolean isLikeDisplayed() {
        return isElementDisplayed(likeButton);
    }

    public boolean isSignInModalOpened() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3))
                    .until(ExpectedConditions.presenceOfElementLocated(SIGN_IN_MODAL));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isEditDisplayed() {
        return isElementDisplayed(editNewsButton);
    }

    @Step("Click 'Back to news'")
    public EcoNewsPage goBackToNews() {
        scrollToElementWithJs(backToNewsButton);
        clickElementWithJs(backToNewsButton);
        wait.until(d -> d.getCurrentUrl().matches(".*/greenCity/news/?$"));
        waitForPageToLoad();
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

    @Step("Open related news at index {index}")
    public NewsDetailsPage openRelatedNews(int index) {
        long previousId = getNewsId();
        String previousTitle = getTitleText();

        WebElement card = getVisibleItem(relatedNewsList, index);
        scrollToElementWithJs(card);
        clickElement(card);

        wait.until(d -> {
            Matcher matcher = NEWS_ID.matcher(d.getCurrentUrl());
            return matcher.find() && Long.parseLong(matcher.group(1)) != previousId;
        });
        wait.until(d -> !getTitleText().equals(previousTitle));

        return new NewsDetailsPage(driver);
    }

    public NewsCardComponent getRelatedNewsCard(int index) {
        return new NewsCardComponent(driver, getVisibleItem(relatedNewsList, index));
    }

}
