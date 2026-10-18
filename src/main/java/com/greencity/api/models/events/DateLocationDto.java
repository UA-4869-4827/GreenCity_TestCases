package com.greencity.api.models.events;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DateLocationDto {
    private String day;
    private String startDate;
    private String finishDate;
    private String startTime;
    private String finishTime;
    private Boolean allDay;
    private String onlineLink;
    private String place;
    private Boolean appliedLinkForAll;
    private Boolean appliedPlaceForAll;
}