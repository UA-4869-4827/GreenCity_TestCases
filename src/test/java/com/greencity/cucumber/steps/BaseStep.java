package com.greencity.cucumber.steps;

import com.greencity.ui.locale.LocaleContext;
import com.greencity.utils.TestValueProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public class BaseStep {

    protected static WebDriver driver;
    protected static List<String> createdPartners = new ArrayList<>();
    protected TestValueProvider provider = new TestValueProvider();

    @Step("init ChromeDriver")
    public void initDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (provider.isHeadless()) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        if (provider.isWindowMaximized() && !provider.isHeadless()) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(provider.getPageLoadTimeout());
        driver.manage().timeouts().scriptTimeout(provider.getScriptTimeout());
    }

    protected void quitDriver() {
        LocaleContext.clear();
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
