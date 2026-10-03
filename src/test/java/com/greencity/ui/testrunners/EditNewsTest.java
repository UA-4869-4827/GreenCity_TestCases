package com.greencity.ui.testrunners;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.CreateNewsPage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditNewsTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_TITLE =
            "Test edit news " + System.currentTimeMillis();

    private static final String NEWS_CONTENT =
            "Original news content";

    private boolean newsPublished = false;

    private NewsDetailsPage createAndOpenNews() {
        new EcoNewsPage(driver)
                .open()
                .createNews()
                .enterTitle(NEWS_TITLE)
                .selectTag(UiMessage.CREATE_NEWS_TAG_NEWS)
                .enterContent(NEWS_CONTENT)
                .publish();

        newsPublished = true;

        return new EcoNewsPage(driver)
                .openSearch()
                .searchNews(NEWS_TITLE)
                .openNewsByTitle(NEWS_TITLE);
    }


    private String createLargeTempFile(long sizeInBytes) throws IOException {
        File tempFile = File.createTempFile("large-test-image", ".jpg");
        tempFile.deleteOnExit(); // Автоматично видалиться після завершення JVM
        try (RandomAccessFile raf = new RandomAccessFile(tempFile, "rw")) {
            raf.setLength(sizeInBytes);
        }
        return tempFile.getAbsolutePath();
    }

    @AfterEach
    void deleteCreatedNews() {
        if (!newsPublished) {
            return;
        }

        try {
            new EcoNewsPage(driver)
                    .open()
                    .openSearch()
                    .searchNews(NEWS_TITLE)
                    .openNewsByTitle(NEWS_TITLE)
                    .deleteNews();
        } catch (Exception e) {
            // Ігноруємо помилку видалення, якщо тест впав до створення
        }
    }

    @Test
    void cancelEditingDoesNotSaveChanges() {

        NewsDetailsPage news = createAndOpenNews();

        String originalTitle = news.getTitleText();
        String originalContent = news.getBodyText();

        CreateNewsPage editPage = news.editNews();

        // Спочатку очищаємо поле, а потім вводимо нове значення
        editPage
                .enterTitle("Changed title")
                .enterContent("Changed content");

        editPage.cancel();

        NewsDetailsPage newsAfterCancel = new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(NEWS_TITLE)
                .openNewsByTitle(NEWS_TITLE);

        assertAll("Cancel editing",
                () -> assertTrue(newsAfterCancel.getNewsId() > 0,
                        "News details page was not opened"),

                () -> assertEquals(originalTitle, newsAfterCancel.getTitleText(),
                        "Title was changed after Cancel"),

                () -> assertEquals(originalContent, newsAfterCancel.getBodyText(),
                        "Content was changed after Cancel")
        );
    }

    @Test
    void imageLargerThan10MbShowsValidationError() throws IOException {

        NewsDetailsPage news = createAndOpenNews();

        CreateNewsPage editPage = news.editNews();

        // Створюємо файл розміром 11 MB (11 * 1024 * 1024 bytes)
        String largeFilePath = createLargeTempFile(11 * 1024 * 1024L);

        editPage.uploadPicture(largeFilePath);

        assertTrue(
                editPage.isImageSizeErrorDisplayed(),
                "Validation error for image larger than 10MB is not displayed"
        );
    }
}