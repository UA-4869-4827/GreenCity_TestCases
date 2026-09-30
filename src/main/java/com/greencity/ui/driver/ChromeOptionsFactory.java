package com.greencity.ui.driver;

import org.openqa.selenium.chrome.ChromeOptions;

/**
 * Shared Chrome flags for local and CI runs.
 * GitHub Actions sets {@code CI=true}; headless also gets sandbox-safe args.
 */
public final class ChromeOptionsFactory {

    private ChromeOptionsFactory() {
    }

    public static ChromeOptions create(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        boolean ci = isCi();
        boolean runHeadless = headless || ci;

        if (runHeadless) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        if (runHeadless || ci) {
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
        }

        return options;
    }

    public static boolean isCi() {
        String ci = System.getenv("CI");
        return ci != null && !"false".equalsIgnoreCase(ci) && !"0".equals(ci);
    }
}
