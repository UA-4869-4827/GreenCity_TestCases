package com.greencity.ui.locale;

import java.util.Locale;

/**
 * UI languages supported by GreenCity. Codes match frontend {@code localStorage.language}.
 */
public enum UiLocale {
    EN("en", "En"),
    UK("uk", "Uk");

    private final String code;
    private final String headerLabel;

    UiLocale(String code, String headerLabel) {
        this.code = code;
        this.headerLabel = headerLabel;
    }

    public String getCode() {
        return code;
    }

    public String getHeaderLabel() {
        return headerLabel;
    }

    public Locale toJavaLocale() {
        return this == UK ? Locale.forLanguageTag("uk") : Locale.ENGLISH;
    }

    public static UiLocale fromCode(String raw) {
        if (raw == null || raw.isBlank()) {
            return EN;
        }
        String normalized = raw.trim().toLowerCase(Locale.ROOT);
        if (normalized.startsWith("uk") || "ua".equals(normalized)) {
            return UK;
        }
        return EN;
    }
}
