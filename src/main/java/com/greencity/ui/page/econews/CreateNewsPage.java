package com.greencity.ui.page.econews;

import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    @FindBy(css = ".tertiary-global-button")
    private WebElement cancelButton;

    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewButton;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishButton;

    private static final String TAG_XPATH_TEMPLATE = "//button[contains(@class,'tag-button')]//span[text()='%s']";

    public CreateNewsPage(WebDriver driver) {
        super(driver);
    }

    public CreateNewsPage enterTitle(String title) {
        titleInput.sendKeys(title);
        return this;
    }

    public CreateNewsPage enterSource(String url) {
        sourceInput.sendKeys(url);
        return this;
    }

    public CreateNewsPage enterContent(String text) {
        contentEditor.sendKeys(text);
        return this;
    }

    public CreateNewsPage uploadPicture(String filePath) {
        pictureUploadInput.sendKeys(filePath);
        return this;
    }

    public CreateNewsPage selectTag(String tagName) {
        String xpath = String.format(TAG_XPATH_TEMPLATE, tagName);
        driver.findElement(By.xpath(xpath)).click();
        return this;
    }

    public CreateNewsPage clickPictureCancel() {
        pictureCancelButton.click();
        return this;
    }

    public CreateNewsPage clickPictureSubmit() {
      pictureSubmitButton.click();
        return this;
    }

    public EcoNewsPage clickCancel() {
        cancelButton.click();
        return new EcoNewsPage(driver);
    }

    public PreviewNewsPage clickPreview() {
        previewButton.click();
        return new PreviewNewsPage(driver);
    }

    public EcoNewsPage clickPublish() {
        publishButton.click();
        return new EcoNewsPage(driver);
    }
}
