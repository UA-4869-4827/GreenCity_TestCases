package com.greencity.api.models.econews.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentDto {
    private Long id;
    private AuthorDto author;
    private Long parentCommentId;
    private String text;
    private String createdDate;
    private String modifiedDate;
    private Integer replies;
    private Integer likes;
    private Integer dislikes;
    private Boolean currentUserLiked;
    private Boolean currentUserDisliked;
    private String status;
    private List<String> additionalImages;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AuthorDto {
        private Long id;
        private String name;
        private String profilePicturePath;
    }

}
