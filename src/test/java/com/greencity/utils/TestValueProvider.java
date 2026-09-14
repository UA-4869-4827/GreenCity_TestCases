package com.greencity.utils;

import com.greencity.config.AppConfig;
import com.greencity.ui.locale.UiLocale;

import java.time.Duration;

public class TestValueProvider {

    private final AppConfig config = AppConfig.get();

    public String getBaseUIUrl() {
        return config.baseUiUrl();
    }

    public String getBaseAPIUrl() {
        return config.baseApiUrl();
    }

    /**
     * Implicit wait must stay zero when the POM uses explicit waits.
     */
    public Duration getImplicitWait() {
        return config.implicitWait();
    }

    public Duration getExplicitWait() {
        return config.explicitWait();
    }

    public Duration getPageLoadTimeout() {
        return config.pageLoadTimeout();
    }

    public Duration getScriptTimeout() {
        return config.scriptTimeout();
    }

    public UiLocale getLocale() {
        return config.locale();
    }

    public String getBrowser() {
        return config.browser();
    }

    public boolean isHeadless() {
        return config.headless();
    }

    public boolean isWindowMaximized() {
        return config.maximizeWindow();
    }

    public String getUserEmail() {
        return config.userEmail();
    }

    public String getUserName() {
        return config.userName();
    }

    public String getUserPassword() {
        return config.userPassword();
    }

    public String getAdminEmail() {
        return config.adminEmail();
    }

    public String getAdminName() {
        return config.adminName();
    }

    public String getAdminPassword() {
        return config.adminPassword();
    }

    public String getJDBCGreenCityUsername() {
        return config.jdbcGreenCityUsername();
    }

    public String getJDBCGreenCityPassword() {
        return config.jdbcGreenCityPassword();
    }

    public String getJDBCGreenCityURL() {
        return config.jdbcGreenCityUrl();
    }
}
