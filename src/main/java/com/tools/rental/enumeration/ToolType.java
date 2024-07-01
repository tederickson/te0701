package com.tools.rental.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ToolType {
    CHAINSAW("Chainsaw"),
    LADDER("Ladder"),
    JACKHAMMER("Jackhammer");

    final String description;
}
