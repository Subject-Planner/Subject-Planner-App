package com.amplifyframework.datastore.generated.model;

import java.util.ArrayList;
import java.util.List;

/** Auto generated enum from GraphQL schema. */
@SuppressWarnings("all")
public enum DaysEnum {
  SATURDAY,
  SUNDAY,
  MONDAY,
  THURSDAY,
  WEDNESDAY,
  TUESDAY,
  FRIDAY;

  public static List<DaysEnum> fromStringList(List<String> days) {
    List<DaysEnum> daysEnums = new ArrayList<>();

    for (String day : days) {
      daysEnums.add(fromString(day));
    }

    return daysEnums;
  }
  public static DaysEnum fromString(String value) {
    for (DaysEnum day : DaysEnum.values()) {
      if (day.name().equalsIgnoreCase(value.trim())) {
        return day;
      }
    }
    throw new IllegalArgumentException("Day unknown: " + value);
  }
  public static List<String> toStringList(List<DaysEnum> daysEnums) {
    List<String> daysStrings = new ArrayList<>();

    for (DaysEnum dayEnum : daysEnums) {
      daysStrings.add(toFirstLetterCapitalizedString(dayEnum.name()));
    }

    return daysStrings;
  }

  private static String toFirstLetterCapitalizedString(String value) {
    return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
  }
}
