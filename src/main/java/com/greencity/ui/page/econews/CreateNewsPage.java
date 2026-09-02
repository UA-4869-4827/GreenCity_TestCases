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

    @FindBy(xpath = "//button[contains(@class,'tertiary-global-button') and normalize-space()='Cancel']")
    private WebElement cancelButton;

    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewButton;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishButton;

    private static final String TAG_XPATH_TEMPLATE = "//button[contains(@class,'tag-button')]//span[normalize-space()=%s]";

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
        String xpath = String.format(TAG_XPATH_TEMPLATE, xpathLiteral(tagName));
        WebElement tag = driver.findElement(By.xpath(xpath));
        clickElement(tag);
        return this;
    }

    private static String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'", -1);
        StringBuilder concat = new StringBuilder("concat('");
        concat.append(String.join("', \"'\", '", parts)).append("')");
        return concat.toString();
    }

    public CreateNewsPage clickPictureCancel() {
        clickElement(pictureCancelButton);
        return this;
    }

    public CreateNewsPage clickPictureSubmit() {
        clickElement(pictureSubmitButton);
        return this;
    }

    public EcoNewsPage clickCancel() {
        clickElement(cancelButton);
        return new EcoNewsPage(driver);
    }

    public PreviewNewsPage clickPreview() {
        clickElement(previewButton);
        return new PreviewNewsPage(driver);
    }

    public EcoNewsPage clickPublish() {
        clickElement(publishButton);
        return new EcoNewsPage(driver);
    }
}
