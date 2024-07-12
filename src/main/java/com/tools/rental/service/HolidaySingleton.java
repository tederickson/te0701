package com.tools.rental.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class HolidaySingleton {
    // Utilize double-checked locking to prevent multiple singletons in multithreaded applications
    private static volatile HolidaySingleton instance;

    private final Map<Integer, List<LocalDate>> holidayMap;

    private HolidaySingleton() {
        holidayMap = new HashMap<>();
    }

    public static HolidaySingleton getInstance() {
        if (instance == null) {
            synchronized (HolidaySingleton.class) {
                if (instance == null) {
                    instance = new HolidaySingleton();
                }
            }
        }
        return instance;
    }

    public List<LocalDate> getHolidays(final LocalDate date) {
        return getHolidays(date.getYear());
    }

    public List<LocalDate> getHolidays(final Integer year) {
        List<LocalDate> holidays = holidayMap.get(year);

        if (holidays != null) {
            return holidays;
        }

        holidays = new ArrayList<>();
        holidays.add(getObservedIndependenceDay(year));
        holidays.add(getLaborDay(year));

        holidayMap.put(year, holidays);

        return holidays;
    }

    private LocalDate getObservedIndependenceDay(Integer year) {
        LocalDate date = LocalDate.of(year, 7, 4);
        DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));

        return switch (day) {
            case SATURDAY -> date.minusDays(1);
            case SUNDAY -> date.plusDays(1);
            default -> date;
        };
    }

    private LocalDate getLaborDay(Integer year) {
        LocalDate date = LocalDate.of(year, 9, 1);
        DayOfWeek day = DayOfWeek.of(date.get(ChronoField.DAY_OF_WEEK));

        return switch (day) {
            case MONDAY -> date;
            case TUESDAY -> date.plusDays(6);
            case WEDNESDAY -> date.plusDays(5);
            case THURSDAY -> date.plusDays(4);
            case FRIDAY -> date.plusDays(3);
            case SATURDAY -> date.plusDays(2);
            case SUNDAY -> date.plusDays(1);
        };
    }
}
