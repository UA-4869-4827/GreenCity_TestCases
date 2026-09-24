package com.greencity.ui.page.econews;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;
import java.util.stream.Collectors;

public class CreateNewsPage extends BasePage {

    @FindBy(css = "textarea[formcontrolname='title']")
    private WebElement titleInput;

    @FindBy(css = ".title-block .field-info")
    private WebElement titleCounter;

    @FindBy(css = "[formcontrolname='source']")
    private WebElement sourceInput;

    @FindBy(css = "[formcontrolname='content'] .ql-editor")
    private WebElement contentEditor;

    @FindBy(id = "upload")
    private WebElement pictureUploadInput;

    @FindBy(css = "app-drag-and-drop label[for='upload'] span")
    private WebElement browseLink;

    @FindBy(css = "app-drag-and-drop .warning")
    private WebElement pictureWarningText;


    @FindBy(css = ".cropper-buttons button.secondary-global-button")
    private WebElement pictureCancelButton;

    @FindBy(css = ".cropper-buttons button.primary-global-button")
    private WebElement pictureSubmitButton;

    @FindBy(css = ".tags-block > p")
    private WebElement tagsLimitHint;


    @FindBy(xpath = "//button[contains(@class,'tag-button')]")
    private WebElement tagButton;

    @FindBy(css = ".tags-box button.tag-button")
    private List<WebElement> tagButtons;

    @FindBy(css = ".textarea-wrapper .field-info")
    private WebElement contentCounterHint;

    @FindBy(css = ".quill-counter.warning")
    private WebElement contentWarning;

    @FindBy(css = ".source-block .field-info")
    private WebElement sourceHint;

    @FindBy(xpath = "//span[normalize-space()='Date:']")
    private WebElement dateLabel;

    @FindBy(xpath = "//span[normalize-space()='Author:']")
    private WebElement authorLabel;

    @FindBy(css = ".date p:nth-of-type(1) span:nth-of-type(2)")
    private WebElement dateValue;

    @FindBy(css = ".date p:nth-of-type(2) span:nth-of-type(2)")
    private WebElement authorValue;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelButton;

    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewButton;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishButton;

    public CreateNewsPage(WebDriver driver) {
        super(driver);
        waitForPageToLoad();
    }

    public CreateNewsPage enterTitle(String title) {
        typeText(titleInput, title);
        return this;
    }

    public String getTitlePlaceholder() {
        return getElementAttribute(titleInput, "placeholder");
    }

    public String getTitleCounterText() {
        return getElementText(titleCounter);
    }

    public CreateNewsPage enterSource(String url) {
        typeText(sourceInput, url);
        return this;
    }

    public CreateNewsPage enterContent(String text) {
        typeText(contentEditor, text);
        return this;
    }

    public String getPictureWarningText() {
        return getElementText(pictureWarningText);
    }

    public CreateNewsPage uploadPicture(String filePath) {
        pictureUploadInput.sendKeys(filePath);
        return this;
    }

    public CreateNewsPage selectTag(String tagName) {
        clickElement(tagButton);
        return this;

    }

    public CreateNewsPage selectTag(UiMessage tag) {
        return selectTag(tag.text());
    }

    public List<String> getTagNames() {
        return tagButtons.stream()
                .map(this::getElementText)
                .collect(Collectors.toList());
    }


    public int getTagsCount() {
        return tagButtons.size();
    }

    public String getTagsLimitHintText() {
        return getElementText(tagsLimitHint);
    }

    public String getContentPlaceholder() {
        return getElementAttribute(contentEditor, "data-placeholder");
    }

    public String getContentCounterHintText() {
        return getElementText(contentCounterHint);
    }

    public String getSourcePlaceholder() {
        return getElementAttribute(sourceInput, "placeholder");
    }

    public String getSourceHintText() {
        return getElementText(sourceHint);
    }

    public String getDateValueText() {
        return getElementText(dateValue).trim();
    }

    public String getAuthorValueText() {
        return getElementText(authorValue).trim();
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

    public boolean isTitleInputVisible() {
        return isElementDisplayed(titleInput);
    }

    public boolean isTagSectionVisible() {
        return isElementDisplayed(tagButton);
    }

    public boolean isAddImageVisible() {
        return isElementDisplayed(pictureSubmitButton);
    }

    public boolean isBrowseLinkVisible() {
        return isElementDisplayed(browseLink);
    }

    public boolean isMainTextVisible() {
        return isElementDisplayed(contentEditor);
    }

    public boolean isAuthorVisible() {
        return isElementDisplayed(authorLabel);
    }

    public boolean isDateVisible() {
        return isElementDisplayed(dateLabel);
    }

    public boolean isSourceInputVisible() {
        return isElementDisplayed(sourceInput);
    }

    public boolean isCancelButtonVisible() {
        return isElementDisplayed(cancelButton);
    }

    public boolean isPreviewButtonVisible() {
        return isElementDisplayed(previewButton);
    }

    public boolean isPublishButtonVisible() {
        return isElementDisplayed(publishButton);
    }

    public boolean isPublishButtonEnabled() {
        return publishButton.isEnabled();
    }

    public boolean isAuthorFieldEditable() {
        String tagName = authorValue.getTagName();
        return "input".equalsIgnoreCase(tagName) || "textarea".equalsIgnoreCase(tagName);
    }

    public boolean isDateFieldEditable() {
        String tagName = dateValue.getTagName();
        return "input".equalsIgnoreCase(tagName) || "textarea".equalsIgnoreCase(tagName);
    }
}
