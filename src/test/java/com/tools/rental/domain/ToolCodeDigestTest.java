package com.tools.rental.domain;

import com.tools.rental.enumeration.Brand;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ToolCodeDigestTest {

    @Test
    void builder() {
        final ToolCode toolCode = ToolCode.LADW;
        final ToolType toolType = toolCode.getToolType();
        final Brand brand = toolCode.getBrand();
        final var builder = ToolCodeDigest.builder()
                .withToolCode(toolCode.name())
                .withToolType(toolType.name())
                .withToolTypeDescription(toolType.getDescription())
                .withWeekdayCharge(toolType.getWeekdayCharge())
                .withWeekendCharge(toolType.getWeekendCharge())
                .withHolidayCharge(toolType.getHolidayCharge())
                .withBrand(brand.name())
                .withBrandDescription(brand.getDescription());

        assertThat(builder.toString(), is(notNullValue()));
    }
}