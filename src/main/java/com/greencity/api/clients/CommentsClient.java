package com.greencity.api.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommentsClient extends BaseClient {

    private static final String NEWS_COMMENTS =
            "/eco-news/{ecoNewsId}/comments";

    private static final String COMMENT =
            "/eco-news/comments/{commentId}";

    private static final String COMMENTS =
            "/eco-news/comments";

    public CommentsClient(String apiBaseUrl) {
        super(apiBaseUrl);
    }

    public CommentsClient(String apiBaseUrl, String accessToken) {
        super(apiBaseUrl);
        setToken(accessToken);
    }

    public Response createComment(
            long newsId,
            String text,
            Long parentCommentId
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("text", text);
        body.put("parentCommentId", parentCommentId);

        String json = toJson(body);

        return preparedRequest()
                .contentType(ContentType.MULTIPART)
                .multiPart("request", json, "application/json")
                .post(NEWS_COMMENTS, newsId);
    }

    public Response getCommentsCount(long newsId) {
        return preparedRequest()
                .get(NEWS_COMMENTS + "/count", newsId);
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    private String toJson(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(
                    "Cannot serialize comment request", e
            );
        }
    }

    public Response getComment(long commentId) {
        return preparedRequest()
                .get(COMMENT, commentId);
    }

    public Response updateComment(long commentId, String text) {
        return preparedRequest()
                .contentType(ContentType.JSON)
                .body(toJson(text))
                .patch(COMMENTS + "?commentId={commentId}", commentId);
    }

    public Response deleteComment(long commentId) {
        return preparedRequest()
                .delete(COMMENT, commentId);
    }

    public Response getRepliesCount(long parentCommentId) {
        return preparedRequest()
                .get(
                        "/eco-news/comments/{commentId}/replies/active/count",
                        parentCommentId
                );
    }

    public Response getActiveReplies(long parentCommentId) {
        return preparedRequest()
                .get(
                        "/eco-news/comments/{commentId}/replies/active",
                        parentCommentId
                );
    }

    public Response getActiveComments(long newsId) {
        return preparedRequest()
                .get(NEWS_COMMENTS + "/active", newsId);
    }
}