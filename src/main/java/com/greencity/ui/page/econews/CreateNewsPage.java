package com.greencity.ui.page.econews;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    @FindBy(xpath = "//app-create-edit-news//p[contains(., 'Date')]")
    private WebElement dateLabel;

    @FindBy(xpath = "//app-create-edit-news//p[contains(., 'Author')]")
    private WebElement authorLabel;

    @FindBy(css = ".date p:nth-of-type(1) span:nth-of-type(2)")
    private WebElement dateValue;

    @FindBy(css = ".date p:nth-of-type(2) span:nth-of-type(2)")
    private WebElement authorValue;

    @FindBy(css = ".submit-buttons button.secondary-global-button")
    private WebElement previewButton;

    @FindBy(css = ".submit-buttons button.primary-global-button")
    private WebElement publishButton;

    @FindBy(css = "app-warning-pop-up .warning-title")
    private WebElement cancelWarningTitle;

    @FindBy(css = "app-warning-pop-up .warning-subtitle")
    private WebElement cancelWarningSubtitle;

    @FindBy(css = "app-warning-pop-up button.secondary-global-button")
    private WebElement continueEditingButton;

    @FindBy(css = "app-warning-pop-up button.primary-global-button")
    private WebElement confirmCancelButton;

    @FindBy(css = "app-create-edit-news h2.title-header")
    private WebElement pageHeading;

    @FindBy(css = "app-tags-select a.global-tag-clicked span.text")
    private List<WebElement> selectedTags;

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

    public CreateNewsPage clearTitle() {
        waitUntilElementVisible(titleInput);
        js.executeScript(
                "const input = arguments[0];"
                        + "input.focus();"
                        + "input.value = '';"
                        + "input.dispatchEvent(new Event('input', {bubbles: true}));"
                        + "input.dispatchEvent(new Event('blur', {bubbles: true}));",
                titleInput);
        return this;
    }

    public boolean isTitleBorderRed() {
        return wait.until(d -> "solid".equals(titleInput.getCssValue("border-top-style"))
                && "1px".equals(titleInput.getCssValue("border-top-width"))
                && "#ff0000".equalsIgnoreCase(
                        Color.fromString(titleInput.getCssValue("border-top-color")).asHex()));
    }

    public boolean isEditButtonDisabled() {
        return !publishButton.isEnabled();
    }

    public CreateNewsPage attemptEdit() {
        publishButton.click();
        return this;
    }

    public String getFormUrl() {
        return driver.getCurrentUrl();
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
        clickBy(By.xpath("//button[contains(@class,'tag-button')]//span[normalize-space()="
                + xpathLiteral(tagName) + "]"));
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

    public CreateNewsPage cancel() {
        clickBy(By.xpath("//button[contains(@class,'tertiary-global-button') and normalize-space()="
                + xpathLiteral(UiMessage.CREATE_NEWS_CANCEL.text()) + "]"));
        return this;
    }

    public PreviewNewsPage preview() {
        clickElement(previewButton);
        return new PreviewNewsPage(driver);
    }

    public EcoNewsPage publish() {
        clickElement(publishButton);
        wait.until(ExpectedConditions.urlMatches(".*/greenCity/news/?$"));
        return new EcoNewsPage(driver);
    }

    public boolean isCancelConfirmationDisplayed() {
        return isElementDisplayed(By.cssSelector("app-warning-pop-up .warning-title"));
    }

    public String getCancelWarningTitleText() {
        waitUntilElementVisible(cancelWarningTitle);
        return getElementText(cancelWarningTitle).trim();
    }

    public String getCancelWarningSubtitleText() {
        waitUntilElementVisible(cancelWarningSubtitle);
        return getElementText(cancelWarningSubtitle).trim();
    }

    public String getContinueEditingButtonText() {
        waitUntilElementVisible(continueEditingButton);
        return getElementText(continueEditingButton).trim();
    }

    public String getConfirmCancelButtonText() {
        waitUntilElementVisible(confirmCancelButton);
        return getElementText(confirmCancelButton).trim();
    }

    public CreateNewsPage continueEditing() {
        clickElement(continueEditingButton);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(
                d -> d.findElements(By.cssSelector("app-warning-pop-up .warning-title")).isEmpty());
        return this;
    }

    public EcoNewsPage confirmCancel() {
        clickElement(confirmCancelButton);
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
        return isElementDisplayed(By.xpath(
                "//button[contains(@class,'tertiary-global-button') and normalize-space()="
                        + xpathLiteral(UiMessage.CREATE_NEWS_CANCEL.text()) + "]"));
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

    public List<String> getVisibleFieldOrder() {
        Map<WebElement, String> fields = new LinkedHashMap<>();
        fields.put(titleInput, "Title");
        fields.put(tagButton, "Tag");
        fields.put(browseLink, "Add Image");
        fields.put(contentEditor, "Content");
        fields.put(authorLabel, "Author");
        fields.put(dateLabel, "Date");
        fields.put(sourceInput, "Source");
        fields.put(driver.findElement(By.xpath(
                "//button[contains(@class,'tertiary-global-button') and normalize-space()="
                        + xpathLiteral(UiMessage.CREATE_NEWS_CANCEL.text()) + "]")), "Cancel");
        fields.put(previewButton, "Preview");
        fields.put(publishButton, "Publish");
        return fields.entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> documentIndex(entry.getKey())))
                .map(Map.Entry::getValue)
                .toList();
    }

    private int documentIndex(WebElement element) {
        Object index = js.executeScript(
                "return Array.prototype.indexOf.call(document.querySelectorAll('*'), arguments[0]);",
                element);
        return index instanceof Number number ? number.intValue() : Integer.MAX_VALUE;
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
