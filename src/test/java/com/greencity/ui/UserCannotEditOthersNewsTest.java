package com.greencity.ui;

import com.greencity.ui.locale.UiMessage;
import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.page.profile.ProfilePage;
import com.greencity.ui.testrunners.AuthenticatedBaseTestRunner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

//[Edit News][Test Case] Edit button not visible to other users#105

public class UserCannotEditOthersNewsTest extends AuthenticatedBaseTestRunner {

    private static final String NEWS_CONTENT = "Test content for the news created by another user.";

    private static final long TIMESTAMP = System.currentTimeMillis();

    private final String NEWS_TITLE = "TestTest another user news" + TIMESTAMP;

    private boolean newsPublished = false;
    private long newsId;

    @Test
    void userCannotEditNewsCreatedByAnotherUser() {

        NewsDetailsPage news = createNewsAsUserA();

        newsId = news.getNewsId();
        news.getHeader().signOut();

        loginAsSecondUser(ProfilePage.class);

        NewsDetailsPage newsAsUserB = new NewsDetailsPage(driver).open(newsId);

        assertFalse(newsAsUserB.isEditNewsButtonPresent(), "Edit news button should not be present for another user's news");
    }

    private NewsDetailsPage createNewsAsUserA() {
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

    @AfterEach
    void deleteCreatedNews() {
        if (!newsPublished) {
            return;
        }
        new NewsDetailsPage(driver).getHeader().signOut();
        loginAsUser(ProfilePage.class);

        new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(NEWS_TITLE)
                .openNewsByTitle(NEWS_TITLE)
                .deleteNews();
    }
}
