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
import java.util.List;

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

    @FindBy(xpath = "//app-create-edit-news//p[contains(., 'Author')]")
    private WebElement authorLabel;

    @FindBy(xpath = "//app-create-edit-news//p[contains(., 'Date')]")
    private WebElement dateLabel;

    @FindBy(css = "app-create-edit-news h2.title-header")
    private WebElement pageHeading;

    @FindBy(css = "app-tags-select a.global-tag-clicked span.text")
    private List<WebElement> selectedTags;

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

    public String getAuthorLabelText() {
        return getElementText(authorLabel);
    }

    public String getDateLabelText() {
        return getElementText(dateLabel);
    }

    public boolean isAuthorEditable() {
        return !authorLabel.findElements(
                By.cssSelector("input, textarea, select, [contenteditable='true']")).isEmpty();
    }

    public boolean isDateEditable() {
        return !dateLabel.findElements(
                By.cssSelector("input, textarea, select, [contenteditable='true']")).isEmpty();
    }

    public String getPageHeadingText() {
        return getElementText(pageHeading);
    }

    public List<String> getSelectedTags() {
        return selectedTags.stream()
                .map(this::getElementText)
                .toList();
    }

    public String getSourceValue() {
        waitUntilElementVisible(sourceInput);
        String value = sourceInput.getDomProperty("value");
        return value == null ? "" : value;
    }
}
