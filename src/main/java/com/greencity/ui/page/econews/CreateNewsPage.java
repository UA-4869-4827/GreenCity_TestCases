package com.greencity.ui.page.econews;

import com.greencity.ui.component.CommentsComponent;
import com.greencity.ui.component.SocialShareComponent;
import com.greencity.ui.page.BasePage;
import com.greencity.ui.page.econews.econewspage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CreateNewsPage extends BasePage {

    private By titleInput = By.cssSelector("[formcontrolname='title']");
    private By sourceInput = By.cssSelector("[formcontrolname='source']");
    private By contentEditor = By.cssSelector("[formcontrolname='content'] .ql-editor");
    private By pictureUploadInput = By.id("upload");
    private By pictureCancelButton = By.cssSelector(".cropper-buttons button.secondary-global-button");
    private By pictureSubmitButton = By.cssSelector(".cropper-buttons button.primary-global-button");
    private By cancelButton = By.cssSelector(".tertiary-global-button");
    private By previewButton = By.cssSelector(".submit-buttons button.secondary-global-button");
    private By publishButton = By.cssSelector(".submit-buttons button.primary-global-button");
    private static final String TAG_XPATH_TEMPLATE = "//button[contains(@class,'tag-button')]//span[text()='%s']";

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage enterTitle(String title) {
        driver.findElement(titleInput).sendKeys(title);
        return this;
    }

    public CreateNewsPage enterSource(String url) {
        driver.findElement(sourceInput).sendKeys(url);
        return this;
    }

    public CreateNewsPage enterContent(String text) {
        driver.findElement(contentEditor).sendKeys(text);
        return this;
    }

    public CreateNewsPage uploadPicture(String filePath) {
        driver.findElement(pictureUploadInput).sendKeys(filePath);
        return this;
    }

    public CreateNewsPage selectTag(String tagName) {
        String xpath = String.format(TAG_XPATH_TEMPLATE, tagName);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    public CreateNewsPage clickPictureCancel() {
        driver.findElement(pictureCancelButton).click();
        return this;
    }

    public CreateNewsPage clickPictureSubmit() {
        driver.findElement(pictureSubmitButton).click();
        return this;
    }

    public EcoNewsPage clickCancel() {
        driver.findElement(cancelButton).click();
        return new EcoNewsPage(driver);
    }

    public PreviewNewsPage clickPreview() {
        driver.findElement(previewButton).click();
        return new PreviewNewsPage(driver);
    }

    public EcoNewsPage clickPublish() {
        driver.findElement(publishButton).click();
        return new EcoNewsPage(driver);
    }
}
