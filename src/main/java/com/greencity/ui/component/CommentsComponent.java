package com.greencity.ui.component;

import com.greencity.ui.modal.SignInModal;
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

    @FindBy(css = ".comment-textarea-wrapper")
    private WebElement commentField;

    @FindBy(css = ".primary-global-button")
    private WebElement addCommentButton;

    public CommentsComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    public int getCommentsCount() {
        return commentItems.size();
    }

    public CommentsComponent likeComment(int index) {
        clickElement(likeButtons.get(index));
        return this;
    }

    public SignInModal likeCommentAsGuest(int index) {
        clickElement(likeButtons.get(index));
        return new SignInModal(driver);
    }

    public CommentsComponent dislikeComment(int index) {
        clickElement(dislikeButtons.get(index));
        return this;
    }

    public CommentsComponent inputComment(String textComment) {
        typeText(commentField, textComment);
        clickElement(addCommentButton);
        return this;
    }

}
