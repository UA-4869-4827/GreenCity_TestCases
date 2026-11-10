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
public class UpdateEventRequestDto {
    private Long id;
    private String title;
    private String description;
    private List<AddEventRequestDto.DateLocationDto> datesLocations;
    private List<String> tags;
    private Boolean open;
}
