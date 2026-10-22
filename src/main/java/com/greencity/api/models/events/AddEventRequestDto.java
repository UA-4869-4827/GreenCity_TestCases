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
    private List<DateLocationDto> datesLocations;
    private List<String> tags;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DateLocationDto {
        private String startDate;
        private String finishDate;
        private AddressDto coordinates;
        private String onlineLink;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class AddressDto {
        private Double latitude;
        private Double longitude;
        private String streetEn;
        private String streetUk;
        private String houseNumber;
        private String cityEn;
        private String cityUk;
        private String regionEn;
        private String regionUk;
        private String countryEn;
        private String countryUk;
        private String formattedAddressEn;
        private String formattedAddressUk;
    }
}
