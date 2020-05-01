package com.taskmanager.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatterUtil {

    @NotNull private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd");

    @Nullable
    public static Date parseDate(@Nullable final String dateInput) throws Exception {
        try {
            return DATE_FORMAT.parse(dateInput);
        } catch (ParseException pe) {
            throw new Exception("Parse date exception: Wrong date format, for instance: 30.1.2020.");
        }
    }
}
