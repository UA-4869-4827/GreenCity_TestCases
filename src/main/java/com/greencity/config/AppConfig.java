package com.greencity.config;

import com.greencity.ui.locale.UiLocale;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;

/**
 * Test/runtime settings. Resolution order: environment variable, system property, config.properties, default.
 */
public final class AppConfig {

    private static final AppConfig INSTANCE = new AppConfig();

    private final Properties properties = new Properties();

    private AppConfig() {
        loadFromClasspath("config.properties");
        loadFromFile(Path.of("src/test/resources/config.properties"));
    }

    public static AppConfig get() {
        return INSTANCE;
    }

    public String baseUiUrl() {
        return get("base.ui.url", "https://www.greencity.cx.ua/#/greenCity");
    }

    public String baseApiUrl() {
        return get("base.api.url", "https://api-greencity.azurewebsites.net/");
    }

    public Duration implicitWait() {
        return Duration.ofSeconds(getInt("implicitWait", getInt("implicitlyWait", 0)));
    }

    public Duration explicitWait() {
        return Duration.ofSeconds(getInt("explicitWait", 10));
    }

    public Duration pageLoadTimeout() {
        return Duration.ofSeconds(getInt("pageLoadTimeout", 30));
    }

    public Duration scriptTimeout() {
        return Duration.ofSeconds(getInt("scriptTimeout", 30));
    }

    public UiLocale locale() {
        return UiLocale.fromCode(get("locale", "en"));
    }

    public String browser() {
        return get("browser", "chrome");
    }

    public boolean headless() {
        return getBoolean("headless", false);
    }

    public boolean maximizeWindow() {
        return getBoolean("window.maximize", true);
    }

    public String userEmail() {
        return get("user.email", "");
    }

    public String userName() {
        return get("user.name", "");
    }

    public String userPassword() {
        return get("user.password", "");
    }

    public String adminEmail() {
        return get("admin.email", "");
    }

    public String adminName() {
        return get("admin.name", "");
    }

    public String adminPassword() {
        return get("admin.password", "");
    }

    public String jdbcGreenCityUsername() {
        return get("JDBCGreenCityUsername", "");
    }

    public String jdbcGreenCityPassword() {
        return get("JDBCGreenCityPassword", "");
    }

    public String jdbcGreenCityUrl() {
        return get("JDBCGreenCityURL", "");
    }

    public String get(String key, String defaultValue) {
        return firstNonBlank(
                getenv(toEnv(key)),
                System.getProperty(key),
                properties.getProperty(key))
                .orElse(defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        String raw = get(key, null);
        if (raw == null || raw.isBlank()) {
            return defaultValue;
        }
        return Integer.parseInt(raw.trim());
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String raw = get(key, null);
        if (raw == null || raw.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(raw.trim());
    }

    private void loadFromClasspath(String resourceName) {
        try (InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
            if (in != null) {
                properties.load(in);
            }
        } catch (IOException ignored) {
            // Fall back to file / env / defaults.
        }
    }

    private void loadFromFile(Path path) {
        if (!Files.isRegularFile(path)) {
            return;
        }
        try (InputStream in = Files.newInputStream(path)) {
            properties.load(in);
        } catch (IOException ignored) {
            // Fall back to env / defaults.
        }
    }

    private static String toEnv(String key) {
        return key.replace('.', '_').replace('-', '_').toUpperCase(Locale.ROOT);
    }

    private static String getenv(String name) {
        String value = System.getenv(name);
        if (value != null) {
            return value;
        }
        if ("IMPLICITWAIT".equals(name)) {
            return System.getenv("IMPLICIT_WAIT");
        }
        if ("EXPLICITWAIT".equals(name)) {
            return System.getenv("EXPLICIT_WAIT");
        }
        if ("PAGELOADTIMEOUT".equals(name)) {
            return System.getenv("PAGE_LOAD_TIMEOUT");
        }
        if ("SCRIPTTIMEOUT".equals(name)) {
            return System.getenv("SCRIPT_TIMEOUT");
        }
        return null;
    }

    private static Optional<String> firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}
