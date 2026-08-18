package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommentsComponent extends BasePage {


    private By commentItems = By.cssSelector("div.comment-body-wrapper");
    private By likeButtons = By.cssSelector("div.comment-likes img[alt='like']");
    private By dislikeButtons = By.cssSelector("div.comment-likes img.dislike-img");


    public CommentsComponent(WebDriver driver) {
        super(driver);
    }


    public int getCommentsCount() {
        return driver.findElements(commentItems).size();
    }


    public SignInModal likeComment(int index) {
        driver.findElements(likeButtons)
                .get(index)
                .click();

        return new SignInModal(driver);
    }

    public CommentsComponent dislikeComment(int index) {
        driver.findElements(dislikeButtons)
                .get(index)
                .click();

        return this;
    }
}