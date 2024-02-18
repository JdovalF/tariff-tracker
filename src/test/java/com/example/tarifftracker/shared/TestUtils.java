package com.example.tarifftracker.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestUtils {

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH.mm.ss");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}
