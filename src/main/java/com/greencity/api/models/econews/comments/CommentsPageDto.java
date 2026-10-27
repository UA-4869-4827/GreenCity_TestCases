package com.greencity.api.models.econews.comments;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsPageDto {
    private List<CommentDto> page;
    private long totalElements;
    private int currentPage;
    private int totalPages;
}
