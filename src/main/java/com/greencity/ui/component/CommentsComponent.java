package com.greencity.ui.component;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CommentsComponent extends BaseComponent {

    public CommentsComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @FindBy(xpath = ".//img[@alt='like']")
    private List<WebElement> likeButtons;

    @FindBy(xpath = ".//img[@alt='dislike']")
    private List<WebElement> dislikeButtons;

    public void likeComment(int index) {
        likeButtons.get(index).click();
    }

    public void dislikeComment(int index) {
        dislikeButtons.get(index).click();
    }
}