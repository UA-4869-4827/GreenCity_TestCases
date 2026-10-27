package com.greencity.api;

import com.greencity.api.clients.CommentsClient;
import com.greencity.api.models.econews.AddEcoNewsDtoRequest;
import com.greencity.api.models.econews.EcoNewsDto;
import com.greencity.api.support.ApiAuth;
import com.greencity.api.testRunners.AuthenticatedApiTestRunner;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CommentsApiTest extends AuthenticatedApiTestRunner {

    private static final String NEWS_CONTENT = "Reference API content for comments, at least twenty characters.";
    private static final String COMMENT_TEXT = "This is a test comment created through the API.";
    private final List<Long> createdCommentIds = new ArrayList<>();
    private Long newsId;

    @BeforeEach
    void createTestNews() {
        AddEcoNewsDtoRequest request = AddEcoNewsDtoRequest.builder()
                .title("API comments " + UUID.randomUUID())
                .text(NEWS_CONTENT)
                .tags(List.of("news"))
                .source("")
                .shortInfo("API comments test")
                .build();

        Response response = ecoNewsClient.create(request);

        assertEquals(201, response.statusCode(), "Test news should be created successfully");

        EcoNewsDto createdNews = response.as(EcoNewsDto.class);
        assertNotNull(createdNews.getId(), "Created test news ID should not be null");

        newsId = createdNews.getId();
    }

    @AfterEach
    void cleanUp() {
        for (Long commentId : createdCommentIds) {
            commentsClient.deleteComment(commentId);
        }
        createdCommentIds.clear();

        if (newsId != null) {
            ecoNewsClient.delete(newsId);
            newsId = null;
        }
    }

    @Test
    @DisplayName("131 - Create comment successfully")
    void createCommentSuccessfully() {
        int countBefore = getCommentCount();

        Response response = commentsClient.createComment(newsId, COMMENT_TEXT, null);

        assertEquals(201, response.statusCode(), "Comment should be created successfully");

        long commentId = response.jsonPath().getLong("id");
        createdCommentIds.add(commentId);
        Response activeResponse = commentsClient.getActiveComments(newsId);

        List<Long> activeCommentIds = activeResponse.jsonPath().getList("page.id", Long.class);

        assertAll(
                () -> assertTrue(commentId > 0, "Created comment ID must be positive"),
                () -> assertEquals(COMMENT_TEXT, response.jsonPath().getString("text"), "Created comment text should match the request"),
                () -> assertEquals(countBefore + 1, getCommentCount(), "Comments count should increase by one after creation"),
                () -> assertTrue(activeCommentIds != null && activeCommentIds.contains(commentId), "Created comment should appear in active comments"));
    }

    @Test
    @DisplayName("132 - Creating a comment without authorization returns 401")
    void createCommentWithoutAuthorizationReturns401() {
        CommentsClient guestClient = new CommentsClient(testValueProvider.getBaseAPIUrl());

        Response response = guestClient.createComment(newsId, COMMENT_TEXT, null);

        assertEquals(401, response.statusCode(), "Unauthenticated user should not be allowed to create a comment");
    }

    @Test
    @DisplayName("133 - Reply to a comment successfully")
    void replyToCommentSuccessfully() {
        Response parentResponse = commentsClient.createComment(newsId, "Parent comment for reply test.", null);

        assertEquals(201, parentResponse.statusCode(), "Parent comment should be created successfully");

        long parentId = parentResponse.jsonPath().getLong("id");
        createdCommentIds.add(parentId);

        int repliesBefore = commentsClient.getRepliesCount(parentId).as(Integer.class);

        String replyText = "This is a reply to the parent comment.";

        Response replyResponse = commentsClient.createComment(newsId, replyText, parentId);

        assertEquals(201, replyResponse.statusCode(), "Reply should be created successfully");

        long replyId = replyResponse.jsonPath().getLong("id");
        createdCommentIds.add(replyId);

        List<Long> replyIds = commentsClient.getActiveReplies(parentId)
                .jsonPath()
                .getList("page.id", Long.class);

        assertAll(
                () -> assertEquals(replyText, replyResponse.jsonPath().getString("text"), "Reply text should match the request"),
                () -> assertEquals(repliesBefore + 1, commentsClient.getRepliesCount(parentId).as(Integer.class), "Replies count should increase by one"),
                () -> assertNotNull(replyIds, "Active replies list should not be null"),
                () -> assertTrue(replyIds != null && replyIds.contains(replyId), "Created reply should appear in the parent's active replies"));
    }

    @Test
    @DisplayName("134 - Author can edit their own comment")
    void authorCanEditOwnComment() {
        long commentId = createCommentAndTrack(COMMENT_TEXT);
        String updatedText = "Updated comment text from API test.";

        Response response = commentsClient.updateComment(commentId, updatedText);

        assertEquals(200, response.statusCode(), "Author should be able to update their own comment");

        Response fetched = commentsClient.getComment(commentId);

        assertEquals(updatedText, fetched.jsonPath().getString("text"), "Updated comment text should match the requested text");
    }

    @Test
    @DisplayName("135 - Second user cannot edit another user's comment")
    void secondUserCannotEditAnotherUsersComment() {
        String originalText = "Original comment for edit permission test.";
        long commentId = createCommentAndTrack(originalText);

        String secondUserToken = ApiAuth.accessTokenForSecondUser(testValueProvider);

        CommentsClient secondUserCommentsClient = new CommentsClient(testValueProvider.getBaseAPIUrl(), secondUserToken);

        String updatedText = "Edited by another user.";

        Response updateResponse = secondUserCommentsClient.updateComment(commentId, updatedText);

        assertEquals(403, updateResponse.statusCode(), "Second user should not be allowed to edit another user's comment");

        Response fetched = commentsClient.getComment(commentId);

        assertEquals(originalText, fetched.jsonPath().getString("text"), "Comment text should remain unchanged after an unauthorized edit attempt");
    }

    @Test
    @DisplayName("136 - Author can delete their own comment")
    void authorCanDeleteOwnComment() {
        int countBefore = getCommentCount();
        long commentId = createCommentAndTrack(COMMENT_TEXT);

        assertEquals(countBefore + 1, getCommentCount(), "Comments count should increase by one after creation");

        Response response = commentsClient.deleteComment(commentId);

        assertEquals(200, response.statusCode(), "Author should be able to delete their own comment");

        createdCommentIds.remove(commentId);

        assertEquals(countBefore, getCommentCount(), "Comments count should return to its original value after deletion");

        assertFalse(getActiveCommentIds().contains(commentId), "Deleted comment should not appear in active comments");
    }

    @Test
    @DisplayName("137 - Second user cannot delete another user's comment")
    void secondUserCannotDeleteAnotherUsersComment() {
        String originalText = "Original comment for delete permission test.";
        long commentId = createCommentAndTrack(originalText);

        String secondUserToken = ApiAuth.accessTokenForSecondUser(testValueProvider);

        CommentsClient secondUserCommentsClient = new CommentsClient(testValueProvider.getBaseAPIUrl(), secondUserToken);

        Response deleteResponse = secondUserCommentsClient.deleteComment(commentId);

        assertEquals(403, deleteResponse.statusCode(), "Second user should not be allowed to delete another user's comment");

        Response fetched = commentsClient.getComment(commentId);

        assertEquals(originalText, fetched.jsonPath().getString("text"), "Comment should remain unchanged after an unauthorized deletion attempt");
    }

    @Test
    @DisplayName("138 - Empty, whitespace-only and overlong comments return 400")
    void invalidCommentTextReturns400() {
        int countBefore = getCommentCount();

        List<String> invalidTexts = List.of(
                "",
                "   ",
                "a".repeat(8001));

        for (String invalidText : invalidTexts) {
            Response response = commentsClient.createComment(newsId, invalidText, null);

            assertEquals(400, response.statusCode(), "Invalid comment text should be rejected with status 400. Length: "
                    + invalidText.length()
                    + ", response: "
                    + response.asPrettyString());
        }

        assertEquals(countBefore, getCommentCount(), "Comments count should remain unchanged after invalid creation attempts");
    }

    private long createCommentAndTrack(String text) {
        Response response = commentsClient.createComment(newsId, text, null);

        assertEquals(201, response.statusCode(), "Test comment should be created successfully. Response: "
                + response.asPrettyString());

        long commentId = response.jsonPath().getLong("id");
        createdCommentIds.add(commentId);

        return commentId;
    }

    private int getCommentCount() {
        return commentsClient.getCommentsCount(newsId).as(Integer.class);
    }

    private List<Long> getActiveCommentIds() {
        Response response = commentsClient.getActiveComments(newsId);

        return response.jsonPath().getList("page.id", Long.class);
    }
}