package com.greencity.api.models.econews;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Body part {@code addEcoNewsDtoRequest} for {@code POST /eco-news} (multipart).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddEcoNewsDtoRequest {

    private String title;
    private String text;
    private List<String> tags;
    private String source;
    private String shortInfo;
}
