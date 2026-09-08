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
        apply(driver, locale, null);
    }

    public static void apply(WebDriver driver, UiLocale locale, String targetUrl) {
        LocaleContext.set(locale);
        WebDriverWait wait = new WebDriverWait(driver, AppConfig.get().explicitWait());
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String stored = (String) js.executeScript("return window.localStorage.getItem('language');");
        boolean languageChanged = !locale.getCode().equals(stored);

        if (languageChanged) {
            js.executeScript("window.localStorage.setItem('language', arguments[0]);", locale.getCode());
        }

        // Always navigate to targetUrl if provided, to avoid staying on a redirected page (e.g. /#/ubs)
        if (targetUrl != null && !targetUrl.isBlank()) {
            driver.get(targetUrl);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("app-header")));
        } else if (languageChanged) {
            driver.navigate().refresh();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("app-header")));
        }
    }
}
