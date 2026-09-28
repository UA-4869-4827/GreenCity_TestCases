package com.greencity.ui.page.econews;

import com.greencity.ui.locale.UiMessage;
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

    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewButton;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishButton;

    @FindBy(css = ".title-block .title-wrapper .field-info")
    private WebElement titleCounter;

    @FindBy(css = ".quill-counter")
    private WebElement contentCounter;

    @FindBy(css = ".source-block .field-info.warning")
    private WebElement sourceWarning;

    @FindBy(xpath = "//h3[normalize-space()='Content']/following-sibling::*[contains(@class,'field-info')]")
    private WebElement contentInfo;

    @FindBy(css = "p.warning.warning-color")
    private WebElement imageWarning;

    public boolean isPublishButtonEnabled() {
        return publishButton.isEnabled();
    }

    public boolean isImageFieldInvalid() {
        return pictureUploadInput.getAttribute("class").contains("invalid");
    }

    public String getImageWarningText() {
        return getElementText(imageWarning).trim();
    }

    public boolean isImageWarningDisplayed() {
        return imageWarning.isDisplayed();
    }

    public boolean isImageWarningRed() {
        return imageWarning.getAttribute("class").contains("warning-color");
    }
    public CreateNewsPage uploadPicture(String filePath) {
        pictureUploadInput.sendKeys(filePath);
        return this;
    }


    public String getContentInfoText() {
        return getElementText(contentInfo).trim();
    }

    public boolean isContentInfoWarning() {
        return contentInfo.getAttribute("class").contains("warning");
    }

    public CreateNewsPage focusSourceField() {
        clickElement(sourceInput);
        return this;
    }

    public String getSourceWarningText() {
        return getElementText(sourceWarning).trim();
    }

    public boolean isSourceWarningDisplayed() {
        return sourceWarning.isDisplayed();
    }

    public boolean isSourceWarningRed() {
        return sourceWarning.getAttribute("class").contains("warning");
    }

    public CreateNewsPage focusTitleField() {
        clickElement(titleInput);
        return this;
    }

    public String getContentText() {
        return contentEditor.getText();
    }


    public String getTitleCounterText() {
        return getElementText(titleCounter).trim();
    }

    public boolean isTitleCounterDisplayed() {
        return titleCounter.isDisplayed();
    }

    public boolean isTitleCounterWarning() {
        return titleCounter.getAttribute("class").contains("warning");
    }

    public boolean isTitleInvalid() {
        return titleInput.getAttribute("class").contains("ng-invalid");
    }


    public String getTitleText() {
        return titleInput.getAttribute("value");
    }

    public String getContentCounterText() {
        return getElementText(contentCounter).trim();
    }


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
}
