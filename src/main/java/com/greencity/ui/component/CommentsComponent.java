package com.greencity.ui.component;

import com.greencity.ui.elements.BaseElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CommentsComponent extends BaseElement {

    public CommentsComponent(WebDriver driver, WebElement rootElement) {
        super(driver, rootElement);
    }

    @FindBy(xpath = ".//img[@alt='like']")
    private List<WebElement> likeButtons;

    @FindBy(xpath = ".//img[@alt='dislike']")
    private List<WebElement> dislikeButtons;

    public void likeComment(int index) {
        waitUntilElementClickable(likeButtons.get(index));
        likeButtons.get(index).click();
    }

    public void dislikeComment(int index) {
        waitUntilElementClickable(dislikeButtons.get(index));
        dislikeButtons.get(index).click();
    }
}
