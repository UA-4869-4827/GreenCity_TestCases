package com.greencity.ui.page.econews;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.component.SocialShareComponent;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.EcoNews.EcoNewsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NewsDetailsPage extends BasePage {

 
    private By likeButton = By.xpath(""); 
    private By backToNewsButton = By.cssSelector("div.button-text"); 
    private By relatedNewsList = By.xpath(""); 

    private CommentsComponent comments;
    private SocialShareComponent socialShare;

    public NewsDetailsPage(WebDriver driver) {
        super(driver);
        this.comments    = new CommentsComponent(driver);
        this.socialShare = new SocialShareComponent(driver);
    }

    public NewsDetailsPage likeArticle() {
        click(likeButton);
        return this;
    }

    public EcoNewsPage goBackToNews() {
        click(backToNewsButton);
        return new EcoNewsPage(driver);
    }

    public NewsDetailsPage openRelatedNews(int index) {
        driver.findElements(relatedNewsList)
                .get(index)
                .click();
        return new NewsDetailsPage(driver);
    }
}
