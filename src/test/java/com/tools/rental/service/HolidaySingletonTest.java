package com.tools.rental.service;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;

class HolidaySingletonTest {

    @Test
    void getHolidays2024() {
        LocalDate checkoutDate = LocalDate.of(2024, 3, 4);
        List<LocalDate> holidays = HolidaySingleton.getInstance().getHolidays(checkoutDate);

        assertThat(holidays, hasSize(2));
        for (var holiday : holidays) {
            switch (holiday.getMonth()) {
                case JULY:
                    assertThat(holiday.getDayOfMonth(), is(4));
                    break;
                case SEPTEMBER:
                    assertThat(holiday.getDayOfMonth(), is(2));
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void getHolidays2023() {
        LocalDate checkoutDate = LocalDate.of(2023, 3, 4);
        List<LocalDate> holidays = HolidaySingleton.getInstance().getHolidays(checkoutDate);

        assertThat(holidays, hasSize(2));
        for (var holiday : holidays) {
            switch (holiday.getMonth()) {
                case JULY, SEPTEMBER:
                    assertThat(holiday.getDayOfMonth(), is(4));
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void getHolidays2022() {
        LocalDate checkoutDate = LocalDate.of(2022, 3, 4);
        List<LocalDate> holidays = HolidaySingleton.getInstance().getHolidays(checkoutDate);

        assertThat(holidays, hasSize(2));
        for (var holiday : holidays) {
            switch (holiday.getMonth()) {
                case JULY:
                    assertThat(holiday.getDayOfMonth(), is(4));
                    break;
                case SEPTEMBER:
                    assertThat(holiday.getDayOfMonth(), is(5));
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void getHolidays2021() {
        LocalDate checkoutDate = LocalDate.of(2021, 3, 4);
        List<LocalDate> holidays = HolidaySingleton.getInstance().getHolidays(checkoutDate);

        assertThat(holidays, hasSize(2));
        for (var holiday : holidays) {
            switch (holiday.getMonth()) {
                case JULY:
                    assertThat(holiday.getDayOfMonth(), is(5));
                    break;
                case SEPTEMBER:
                    assertThat(holiday.getDayOfMonth(), is(6));
                    break;
                default:
                    fail();
            }
        }
    }

    @Test
    void getHolidays2020() {
        LocalDate checkoutDate = LocalDate.of(2020, 3, 4);
        List<LocalDate> holidays = HolidaySingleton.getInstance().getHolidays(checkoutDate);

        assertThat(holidays, hasSize(2));
        for (var holiday : holidays) {
            switch (holiday.getMonth()) {
                case JULY:
                    assertThat(holiday.getDayOfMonth(), is(3));
                    break;
                case SEPTEMBER:
                    assertThat(holiday.getDayOfMonth(), is(7));
                    break;
                default:
                    fail();
            }
        }
    }
}