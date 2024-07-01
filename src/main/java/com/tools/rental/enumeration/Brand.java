package com.tools.rental.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Brand {
    STIHL("Stihl"),
    WERNER("Werner"),
    DE_WALT("DeWalt"),
    RIDGID("Ridgid");

    final String description;
}
