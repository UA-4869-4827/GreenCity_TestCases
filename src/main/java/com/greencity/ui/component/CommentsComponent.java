package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
<<<<<<< HEAD
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CommentsComponent extends BaseComponent {

    @FindBy(xpath = ".//div[contains(@class,'comment-body-wrapper')]")
    private List<WebElement> commentItems;

    @FindBy(xpath = ".//div[contains(@class,'comment-likes')]//img[@alt='like']")
    private List<WebElement> likeButtons;

    @FindBy(xpath = ".//div[contains(@class,'comment-likes')]//img[contains(@class,'dislike-img')]")
    private List<WebElement> dislikeButtons;

    public CommentsComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public int getCommentsCount() {
        return commentItems.size();
    }

    public SignInModal likeComment(int index) {
        clickElement(likeButtons.get(index));
        return new SignInModal(driver);
    }

    public CommentsComponent dislikeComment(int index) {
        clickElement(dislikeButtons.get(index));
        return this;
    }
}
=======
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CommentsComponent extends BasePage {


    private By commentItems = By.cssSelector("div.comment-body-wrapper");
    private By likeButtons = By.cssSelector("div.comment-likes img[alt='like']");
    private By dislikeButtons = By.cssSelector("div.comment-likes img.dislike-img");
    private By commentField = By.cssSelector(".comment-textarea-wrapper");
    private By addCommentButton = By.cssSelector(".primary-global-button");
    public CommentsComponent(WebDriver driver) {
        super(driver);
    }


    public int getCommentsCount() {
        return driver.findElements(commentItems).size();
    }

   public CommentsComponent inputComment(String textComment) {
       click(commentField);
       driver.findElement(commentField).sendKeys(textComment);
       click(addCommentButton);
       return this;
}

    public SignInModal likeCommentAsGuest(int index) {
        driver.findElements(likeButtons)
                .get(index)
                .click();

        return new SignInModal(driver);
    }

    public CommentsComponent likeComment(int index) {
    driver.findElements(likeButtons)
            .get(index)
            .click();
    return this;
}


    public CommentsComponent dislikeComment(int index) {
        driver.findElements(dislikeButtons)
                .get(index)
                .click();

        return this;
    }
}
>>>>>>> 412c350 (same fixes)
