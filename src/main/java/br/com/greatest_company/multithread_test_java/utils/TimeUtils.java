package br.com.greatest_company.multithread_test_java.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class TimeUtils {

    private TimeUtils() {}

    public static final Instant now() {
        return LocalDateTime.now().toInstant(ZoneOffset.UTC);
    }
}
