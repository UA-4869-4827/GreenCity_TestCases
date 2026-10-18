package com.greencity.api.models.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddEventRequestDto {
    private String title;
    private String description;
    private Boolean open;
    private Integer duration;
    private List<DateLocationDto> datesLocations;
    private List<String> tags;
}