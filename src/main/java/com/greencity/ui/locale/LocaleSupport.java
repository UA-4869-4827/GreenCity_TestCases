package com.greencity.ui.locale;

import com.greencity.config.AppConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Applies the configured UI language the same way GreenCity does: {@code localStorage.language}.
 */
public final class LocaleSupport {

    private LocaleSupport() {
    }

    public static void apply(WebDriver driver, UiLocale locale) {
        LocaleContext.set(locale);
        WebDriverWait wait = new WebDriverWait(driver, AppConfig.get().explicitWait());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String stored = (String) js.executeScript("return window.localStorage.getItem('language');");
        if (locale.getCode().equals(stored)) {
            return;
        }
        js.executeScript("window.localStorage.setItem('language', arguments[0]);", locale.getCode());
        driver.navigate().refresh();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("app-header")));
    }
}
