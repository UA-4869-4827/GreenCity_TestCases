package com.greencity.ui;


import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;

public abstract class Base {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Actions actions;

    public Base(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.js = (JavascriptExecutor) driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(this.driver, this);
    }


    @Step("Refresh the page")
    public void refreshPage() {
        driver.navigate().refresh();
    }

    public void sleep(long millisSeconds) {
        try {
            Thread.sleep(millisSeconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected boolean isElementDisplayed(WebElement element) {
        return element.isDisplayed();

    }

    protected boolean isElementInvisible(WebElement element) {
        return new WebDriverWait(driver, Duration.ofSeconds(2))
            .until(ExpectedConditions.invisibilityOf(element));
    }

    @Step("Type text into the field")
    protected void typeText(WebElement element, String text) {
        waitUntilElementVisible(element);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Check if content is truncated or overflows")
    protected boolean isContentTruncatedOrOverflow(WebElement element) {
        String script = "var element = arguments[0];" + "var computedStyle = window.getComputedStyle(element);" + "var isOverflowing = element.scrollHeight > element.clientHeight || element.scrollWidth > element.clientWidth;" + "var isTextOverflowing = computedStyle.overflow === 'hidden' || computedStyle.textOverflow === 'ellipsis' || computedStyle.whiteSpace === 'nowrap';" + "return isOverflowing && !isTextOverflowing;";

        Boolean isOverflowing;
        isOverflowing = (Boolean) js.executeScript(script, element);

        return isOverflowing != null && isOverflowing;
    }
//    Gets
    protected String getElementText(WebElement element) {
        waitUntilElementVisible(element);
        return element.getText().trim();
    }

    protected String getElementAttribute(WebElement element, String attribute) {
        return element.getDomAttribute(attribute);
    }
    protected String getTitle(){
        return driver.getTitle();
    }

    protected String getCurrentUrl(){
        return driver.getCurrentUrl();
    }

//    Scrolls
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

//    Clicks
    @Step("Click on the element")
    protected void clickElementWithJs(WebElement element) {
        waitUntilElementVisible(element);
        js.executeScript("arguments[0].click();", element);
    }

    @Step("Click on the element")
    protected void clickElement(WebElement element) {
        waitUntilElementClickable(element);
        element.click();
    }

//    Waits
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

    public void waitForPageToLoad(long timeoutInSeconds) {
        new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds)).until((ExpectedCondition<Boolean>) wd -> ((JavascriptExecutor) wd)
                .executeScript("return document.readyState")
                .equals("complete"));
    }
    public void waitForBodyToBePresent() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
    }
}
