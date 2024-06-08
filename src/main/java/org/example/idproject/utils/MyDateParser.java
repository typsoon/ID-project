package org.example.idproject.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class MyDateParser {
    public static Date parseFrom(LocalDate date, String timestamp) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");


        if (date != null && timestamp != null) {
            try {
                LocalTime time = LocalTime.parse(timestamp, timeFormatter);
                LocalDateTime dateTime = LocalDateTime.of(date, time);
                ZonedDateTime zonedDateTime = dateTime.atZone(ZoneId.systemDefault());
                Instant instant = zonedDateTime.toInstant();
                return new Date(instant.toEpochMilli());
            } catch (DateTimeParseException ignored) {
            }
        }

        return Date.from(Instant.now());
    }

    private MyDateParser() {}
}
