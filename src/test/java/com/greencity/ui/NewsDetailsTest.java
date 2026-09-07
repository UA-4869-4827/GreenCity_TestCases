package com.greencity.ui;

import com.greencity.ui.page.econews.EcoNewsPage;
import com.greencity.ui.page.econews.NewsDetailsPage;
import com.greencity.ui.testrunners.BaseTestRunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NewsDetailsTest extends BaseTestRunner {

    private static final String FIXTURE_NEWS_TITLE = "News for comment testing";

    @Test
    void singleViewShowsContentAndReturnsToList() {
        NewsDetailsPage details = new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(FIXTURE_NEWS_TITLE)
                .openNewsByTitle(FIXTURE_NEWS_TITLE);

        assertAll("Article content",
                () -> assertTrue(details.getNewsId() > 0,
                        "URL is not a news details page"),
                () -> assertEquals(FIXTURE_NEWS_TITLE, details.getTitleText(),
                        "Opened article title does not match the fixture"),
                () -> assertFalse(details.getBodyText().isBlank(),
                        "Article body is empty"),
                () -> assertFalse(details.getDateText().isBlank(),
                        "Publication date is not shown"),
                () -> assertFalse(details.getAuthorText().isBlank(),
                        "Author is not shown"));

        assertAll("Share icons",
                () -> assertTrue(details.getSocialShare().isTwitterDisplayed(),
                        "Twitter share icon is missing"),
                () -> assertTrue(details.getSocialShare().isLinkedInDisplayed(),
                        "LinkedIn share icon is missing"),
                () -> assertTrue(details.getSocialShare().isFacebookDisplayed(),
                        "Facebook share icon is missing"));

        assertAll("Related news and comments",
                () -> assertTrue(details.isRelatedNewsDisplayed(),
                        "'May be interesting for you' widget is not visible"),
                () -> assertTrue(details.getRelatedNewsCount() > 0,
                        "Related widget contains no cards"),
                () -> assertTrue(details.getComments().isCommentsCountDisplayed(),
                        "Comments count is not displayed"));

        assertFalse(details.isEditDisplayed(), "Edit is visible for a guest");


        EcoNewsPage newsList = details.goBackToNews();
        assertTrue(newsList.isNewsListOpened(),
                "'Back to news' did not return to the news list");
    }

    @Test
    void guestLikeIsDisabledAndRelatedCardOpensAnotherArticle() {
        NewsDetailsPage details = new EcoNewsPage(driver)
                .open()
                .openSearch()
                .searchNews(FIXTURE_NEWS_TITLE)
                .openNewsByTitle(FIXTURE_NEWS_TITLE);

        assertAll("Guest like control",
                () -> assertTrue(details.isLikeDisplayed(),
                        "Like control is missing"),
                () -> assertTrue(details.isLikeDisabled(),
                        "Like control has no 'disable' class for a guest"));
        details.likeArticle();
        assertFalse(details.isSignInModalOpened(),
                "Sign in modal opened after clicking the article like");

        long originalId = details.getNewsId();
        NewsDetailsPage relatedArticle = details.openRelatedNews(0);

        assertAll("Related article",
                () -> assertNotEquals(originalId, relatedArticle.getNewsId(),
                        "Related card opened the same article"),
                () -> assertFalse(relatedArticle.getTitleText().isBlank(),
                        "Related article has no title"));
    }
}

