package com.greencity.logging;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Writes the outcome of each test into the run journal.
 * Passwords, tokens and emails are not logged here.
 */
public class TestRunLoggingExtension implements TestWatcher, BeforeEachCallback {

    private static final Logger LOG = LoggerFactory.getLogger(TestRunLoggingExtension.class);

    @Override
    public void beforeEach(ExtensionContext context) {
        LOG.info("STARTED {}", display(context));
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        LOG.info("PASSED {}", display(context));
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        LOG.error("FAILED {} - {}", display(context), cause.toString(), cause);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        String reason = cause == null ? "assumption failed" : cause.getMessage();
        LOG.warn("SKIPPED {} - {}", display(context), reason);
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        LOG.info("DISABLED {} - {}", display(context), reason.orElse("no reason"));
    }

    private static String display(ExtensionContext context) {
        return context.getRequiredTestClass().getSimpleName() + " > " + context.getDisplayName();
    }
}
