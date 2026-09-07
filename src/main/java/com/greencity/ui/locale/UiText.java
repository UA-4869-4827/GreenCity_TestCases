package com.greencity.ui.locale;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Map;
import java.util.Properties;

/**
 * Loads UTF-8 message bundles. Prefer language-independent locators; use this when the UI only exposes text.
 */
public final class UiText {

    private static final Map<UiLocale, Properties> BUNDLES = loadBundles();

    private UiText() {
    }

    public static String get(UiMessage message) {
        return get(message.key());
    }

    public static String get(String key) {
        Properties bundle = BUNDLES.get(LocaleContext.get());
        String value = bundle.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException(
                    "Missing i18n key '" + key + "' for locale " + LocaleContext.get().getCode());
        }
        return value;
    }

    private static Map<UiLocale, Properties> loadBundles() {
        Map<UiLocale, Properties> bundles = new EnumMap<>(UiLocale.class);
        for (UiLocale locale : UiLocale.values()) {
            bundles.put(locale, load(locale));
        }
        return bundles;
    }

    private static Properties load(UiLocale locale) {
        String resource = "/i18n/messages_" + locale.getCode() + ".properties";
        try (InputStream in = UiText.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new IllegalStateException("Missing resource " + resource);
            }
            Properties properties = new Properties();
            try (Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
            return properties;
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load " + resource, e);
        }
    }
}
