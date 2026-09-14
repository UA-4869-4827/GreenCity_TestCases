package com.greencity.ui.testrunners;

import com.greencity.ui.locale.LocaleContext;
import com.greencity.ui.locale.LocaleSupport;
import com.greencity.ui.page.homepage.HomePage;
import com.greencity.utils.TestValueProvider;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseTestRunner {
    protected WebDriver driver;
    protected static TestValueProvider testValueProvider;
    protected HomePage homePage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        testValueProvider = new TestValueProvider();
    }

    @BeforeEach
    void setUp() {
        initDriver();
        driver.get(testValueProvider.getBaseUIUrl());
        LocaleSupport.apply(driver, testValueProvider.getLocale());
        homePage = new HomePage(driver);
    }

    @Step("init ChromeDriver")
    public void initDriver() {
        ChromeOptions options = new ChromeOptions();
        if (testValueProvider.isHeadless()) {
            options.addArguments("--headless=new");
        }

        driver = new ChromeDriver(options);
        if (testValueProvider.isWindowMaximized() && !testValueProvider.isHeadless()) {
            driver.manage().window().maximize();
        }
        driver.manage().timeouts().implicitlyWait(Duration.ZERO);
        driver.manage().timeouts().pageLoadTimeout(testValueProvider.getPageLoadTimeout());
        driver.manage().timeouts().scriptTimeout(testValueProvider.getScriptTimeout());
    }

    @AfterEach
    void tearDown() {
        LocaleContext.clear();
        if (driver != null) {
            driver.quit();
        }
    }
}
