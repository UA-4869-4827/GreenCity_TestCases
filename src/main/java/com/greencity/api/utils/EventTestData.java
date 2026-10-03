package com.greencity.api.utils;

import com.greencity.api.models.events.AddEventRequestDto;
import com.greencity.api.models.events.DateLocationDto;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

public final class EventTestData {

    private static final DateTimeFormatter UTC_ISO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm");
    private static final ZoneId LOCAL_ZONE = ZoneId.of("Europe/Kyiv");

    private EventTestData() {
    }

    public static AddEventRequestDto validEvent() {
        ZonedDateTime start = ZonedDateTime.now(ZoneOffset.UTC)
                .plusDays(7)
                .withSecond(0)
                .withNano(0);
        ZonedDateTime finish = start.plusHours(2);

        DateLocationDto dateLocation = DateLocationDto.builder()
                .day(start.truncatedTo(ChronoUnit.DAYS).format(UTC_ISO))
                .startDate(start.format(UTC_ISO))
                .finishDate(finish.format(UTC_ISO))
                .startTime(start.withZoneSameInstant(LOCAL_ZONE).format(TIME))
                .finishTime(finish.withZoneSameInstant(LOCAL_ZONE).format(TIME))
                .allDay(false)
                .onlineLink("https://www.greencity.cx.ua/#/greenCity/events/create-update-event")
                .place("")
                .appliedLinkForAll(false)
                .appliedPlaceForAll(false)
                .build();

        return AddEventRequestDto.builder()
                .title("Auto test event " + UUID.randomUUID().toString().substring(0, 8))
                .description("<p>Description for automated API test, long enough</p>")
                .open(true)
                .duration(1)
                .tags(List.of("Economic"))
                .datesLocations(List.of(dateLocation))
                .build();
    }
}