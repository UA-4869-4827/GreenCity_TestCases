package com.greencity.ui.locale;

import com.greencity.config.AppConfig;

/**
 * Per-thread UI locale used by {@link UiText} when building language-aware locators.
 */
public final class LocaleContext {

    private static final ThreadLocal<UiLocale> CURRENT =
            ThreadLocal.withInitial(() -> AppConfig.get().locale());

    private LocaleContext() {
    }

    public static UiLocale get() {
        return CURRENT.get();
    }

    public static void set(UiLocale locale) {
        CURRENT.set(locale == null ? AppConfig.get().locale() : locale);
    }

    public static void clear() {
        CURRENT.remove();
    }
}
