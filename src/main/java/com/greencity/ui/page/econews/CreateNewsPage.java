package com.greencity.ui.page.econews;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CreateNewsPage extends BasePage {

    @FindBy(css = "[formcontrolname='title']")
    private WebElement titleInput;

    @FindBy(css = "[formcontrolname='source']")
    private WebElement sourceInput;

    @FindBy(css = "[formcontrolname='content'] .ql-editor")
    private WebElement contentEditor;

    @FindBy(id = "upload")
    private WebElement pictureUploadInput;

    @FindBy(css = ".cropper-buttons button.secondary-global-button")
    private WebElement pictureCancelButton;

    @FindBy(css = ".cropper-buttons button.primary-global-button")
    private WebElement pictureSubmitButton;

    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewButton;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishButton;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage enterTitle(String title) {
        typeText(titleInput, title);
        return this;
    }

    public CreateNewsPage enterSource(String url) {
        typeText(sourceInput, url);
        return this;
    }

    public CreateNewsPage enterContent(String text) {
        typeText(contentEditor, text);
        return this;
    }

    public CreateNewsPage uploadPicture(String filePath) {
        pictureUploadInput.sendKeys(filePath);
        return this;
    }

    public CreateNewsPage selectTag(String tagName) {
        clickBy(By.xpath("//button[contains(@class,'tag-button')]//span[normalize-space()="
                + xpathLiteral(tagName) + "]"));
        return this;
    }

    public CreateNewsPage selectTag(UiMessage tag) {
        return selectTag(tag.text());
    }

    public CreateNewsPage clickPictureCancel() {
        clickElement(pictureCancelButton);
        return this;
    }

    public CreateNewsPage clickPictureSubmit() {
        clickElement(pictureSubmitButton);
        return this;
    }

    public EcoNewsPage cancel() {
        clickBy(By.xpath("//button[contains(@class,'tertiary-global-button') and normalize-space()="
                + xpathLiteral(UiMessage.CREATE_NEWS_CANCEL.text()) + "]"));
        return new EcoNewsPage(driver);
    }

    public PreviewNewsPage preview() {
        clickElement(previewButton);
        return new PreviewNewsPage(driver);
    }

    public EcoNewsPage publish() {
        clickElement(publishButton);
        return new EcoNewsPage(driver);
    }

    public String getTitleValue() {
        waitUntilElementVisible(titleInput);
        String value = titleInput.getDomProperty("value");
        return value == null ? "" : value;
    }

    public String getContentText() {
        return getElementText(contentEditor);
    }

    public boolean isFormDisplayed() {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOf(titleInput));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
}
