package com.tools.rental.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ToolType {
    LADDER("Ladder", true, true, false),
    CHAINSAW("Chainsaw", true, false, true),
    JACKHAMMER("Jackhammer", true, false, false);

    final String description;
    final Boolean weekdayCharge;
    final Boolean weekendCharge;
    final Boolean holidayCharge;
}
