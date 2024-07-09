package com.tools.rental.domain;

public record ToolCodeDigest(String toolCode,
                             String toolType,
                             String toolTypeDescription,
                             String brand,
                             String brandDescription,
                             Boolean weekdayCharge,
                             Boolean weekendCharge,
                             Boolean holidayCharge) {
}
