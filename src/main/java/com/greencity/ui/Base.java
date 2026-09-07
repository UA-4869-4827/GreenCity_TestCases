package com.greencity.ui;


import com.greencity.config.AppConfig;
import com.greencity.ui.locale.UiMessage;
import io.qameta.allure.Step;
import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.DefaultElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public abstract class Base {
    @Getter
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public Base(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, AppConfig.get().explicitWait());
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
    }

    protected void initPageElements() {
        PageFactory.initElements(driver, this);
    }

    protected void initNestedElements(SearchContext root) {
        PageFactory.initElements(new DefaultElementLocatorFactory(root), this);
    }

    public void open(String pageHash) {
        String currentUrl = driver.getCurrentUrl();
        String origin = currentUrl.contains("#")
                ? currentUrl.substring(0, currentUrl.indexOf('#'))
                : currentUrl;
        origin = origin.replaceAll("/$", "");
        driver.get(origin + pageHash);
        waitForPageToLoad();
    }

    @Step("Refresh the page")
    public void refreshPage() {
        driver.navigate().refresh();
        waitForPageToLoad();
    }

    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException | TimeoutException e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(By locator) {
        try {
            List<WebElement> found = driver.findElements(locator);
            return !found.isEmpty() && found.get(0).isDisplayed();
        } catch (StaleElementReferenceException | TimeoutException e) {
            return false;
        }
    }

    protected boolean isElementDisplayed(WebElement root, By locator) {
        try {
            List<WebElement> found = root.findElements(locator);
            return !found.isEmpty() && found.get(0).isDisplayed();
        } catch (StaleElementReferenceException | TimeoutException e) {
            return false;
        }
    }

    protected boolean isElementInvisible(WebElement element) {
        try {
            return new WebDriverWait(driver, Duration.ofSeconds(2))
                    .until(ExpectedConditions.invisibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
    }

    @Step("Type text into the field")
    protected void typeText(WebElement element, String text) {
        waitUntilElementVisible(element);
        try {
            element.clear();
        } catch (InvalidElementStateException e) {
            element.sendKeys(Keys.chord(selectAllModifier(), "a"), Keys.DELETE);
        }
        element.sendKeys(text);
    }

    private static Keys selectAllModifier() {
        String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
        return os.contains("mac") ? Keys.COMMAND : Keys.CONTROL;
    }

    @Step("Check if content is truncated or overflows")
    protected boolean isContentTruncatedOrOverflow(WebElement element) {
        String script = "var element = arguments[0];"
                + "var computedStyle = window.getComputedStyle(element);"
                + "var isOverflowing = element.scrollHeight > element.clientHeight"
                + " || element.scrollWidth > element.clientWidth;"
                + "var isTextOverflowing = computedStyle.overflow === 'hidden'"
                + " || computedStyle.textOverflow === 'ellipsis'"
                + " || computedStyle.whiteSpace === 'nowrap';"
                + "return isOverflowing && !isTextOverflowing;";

        Boolean isOverflowing = (Boolean) js.executeScript(script, element);
        return isOverflowing != null && isOverflowing;
    }

    protected String getElementText(WebElement element) {
        waitUntilElementVisible(element);
        return element.getText().trim();
    }

    protected String getElementAttribute(WebElement element, String attribute) {
        waitUntilElementVisible(element);
        String value = element.getDomAttribute(attribute);
        return value == null ? "" : value;
    }

    protected String getTitle() {
        return driver.getTitle();
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    @Step("Scroll to the element")
    public void scrollToElementWithActions(WebElement element) {
        actions.moveToElement(element).perform();
    }

    @Step("Scroll to the element")
    public void scrollToElementWithJs(WebElement element) {
        waitUntilElementVisible(element);
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
        waitUntilElementClickable(element);
    }

    @Step("Scroll to the middle of the page")
    public void scrollToMiddlePage() {
        Number startY = (Number) js.executeScript("return window.pageYOffset;");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight/2)");

        wait.until(driver -> {
            Number currentY = (Number) js.executeScript("return window.pageYOffset;");
            return currentY.doubleValue() != startY.doubleValue();
        });
    }

    @Step("Scroll to the end of the page")
    public void scrollToEndOfPage() {
        js.executeScript("window.scrollTo({ top: document.body.scrollHeight, behavior: 'smooth' });");
    }

    protected int getContentHeight() {
        return ((Number) Objects.requireNonNull(js.executeScript("return document.body.scrollHeight;"))).intValue();
    }

    @Step("Click on the element")
    protected void clickElementWithJs(WebElement element) {
        waitUntilElementVisible(element);
        js.executeScript("arguments[0].click();", element);
    }

    @Step("Click on the element")
    protected void clickElement(WebElement element) {
        waitUntilElementClickable(element);
        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            scrollToElementWithJs(element);
            waitUntilElementClickable(element);
            element.click();
        }
    }

    protected void clickBy(By locator) {
        waitUntilElementPresent(locator);
        clickElement(driver.findElement(locator));
    }

    protected WebElement findIn(WebElement root, By locator) {
        wait.until(d -> !root.findElements(locator).isEmpty());
        WebElement element = root.findElement(locator);
        waitUntilElementVisible(element);
        return element;
    }

    protected WebElement getVisibleItem(List<WebElement> elements, int index) {
        try {
            wait.until(d -> elements != null && elements.size() > index);
            WebElement item = elements.get(index);
            waitUntilElementVisible(item);
            return item;
        } catch (TimeoutException e) {
            int size = elements == null ? 0 : elements.size();
            throw new IllegalArgumentException(
                    "No visible element at index " + index + " (found " + size + ")", e);
        }
    }

    protected void waitUntilUrlContains(String fragment) {
        wait.until(ExpectedConditions.urlContains(fragment));
        waitForPageToLoad();
    }

    protected static String xpathLiteral(String value) {
        if (value == null) {
            return "''";
        }
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        String[] parts = value.split("'", -1);
        return "concat('" + String.join("', \"'\", '", parts) + "')";
    }

    protected By byNormalizedText(String xpathPrefix, UiMessage message) {
        return By.xpath(xpathPrefix + "[normalize-space()=" + xpathLiteral(message.text()) + "]");
    }

    protected By byNormalizedTextContains(String xpathPrefix, UiMessage message) {
        return By.xpath(xpathPrefix + "[contains(normalize-space(.), " + xpathLiteral(message.text()) + ")]");
    }

    protected void waitUntilElementVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitUntilElementInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitUntilElementClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void waitUntilElementPresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitUntilAllElementsVisible(List<WebElement> elements) {
        wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }

    public void waitForPageToLoad() {
        waitForPageToLoad(AppConfig.get().pageLoadTimeout().toSeconds());
    }

    public void waitForPageToLoad(long timeoutInSeconds) {
        WebDriverWait loadWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        loadWait.until((ExpectedCondition<Boolean>) wd -> "complete".equals(
                ((JavascriptExecutor) wd).executeScript("return document.readyState")));
        try {
            new WebDriverWait(driver, Duration.ofSeconds(3)).until((ExpectedCondition<Boolean>) wd -> {
                Object stable = ((JavascriptExecutor) wd).executeScript(
                        "if (!window.getAllAngularTestabilities) { return true; }"
                                + "return window.getAllAngularTestabilities().every(function(t) { return t.isStable(); });");
                return Boolean.TRUE.equals(stable);
            });
        } catch (TimeoutException ignored) {
            // Production Angular often keeps a testability busy (polling, sockets).
        }
        waitUntilElementPresent(By.cssSelector("app-header, body"));
    }

    public void waitForBodyToBePresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }
}
