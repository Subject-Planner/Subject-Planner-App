package com.demo.subjectplanner.activity.database;

import androidx.room.TypeConverter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayConverter {
    private static final String DELIMITER = ",";

    @TypeConverter
    public static String fromList(List<DayOfWeek> days) {
        if (days == null || days.isEmpty()) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (DayOfWeek day : days) {
            builder.append(day.name()).append(DELIMITER);
        }

        builder.setLength(builder.length() - 1);

        return builder.toString();
    }

    @TypeConverter
    public static List<DayOfWeek> toList(String daysString) {
        if (daysString == null || daysString.isEmpty()) {
            return Collections.emptyList();
        }

        String[] dayStrings = daysString.split(DELIMITER);
        List<DayOfWeek> dayList = new ArrayList<>();

        for (String dayString : dayStrings) {
            try {
                DayOfWeek day = DayOfWeek.valueOf(dayString);
                dayList.add(day);
            } catch (IllegalArgumentException e) {
                // Handle invalid day string if needed
            }
        }

        return dayList;
    }

    public static List<DayOfWeek> convertToDayOfWeekList(List<String> selectedDayNames) {
        List<DayOfWeek> selectedDays = new ArrayList<>();

        for (String dayName : selectedDayNames) {
            DayOfWeek day = getDayOfWeekByName(dayName);
            if (day != null) {
                selectedDays.add(day);
            }
        }

        return selectedDays;
    }

    private static DayOfWeek getDayOfWeekByName(String dayName) {
        try {
            return DayOfWeek.valueOf(dayName.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            // Handle invalid day name if needed
            return null;
        }
    }
}

