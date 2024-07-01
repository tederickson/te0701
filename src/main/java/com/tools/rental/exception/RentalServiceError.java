package com.tools.rental.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RentalServiceError {
    MISSING_EMAIL("Missing email."),
    INVALID_PASSWORD("Invalid password."),
    INVALID_NAME("Missing name."),
    INVALID_PAGE_SIZE("Invalid page size"),
    INVALID_PAGE_OFFSET("Invalid page number"),
    INVALID_CONFIRMATION_CODE("Invalid confirmation code."),
    EXISTING_USER("User exists."),
    NOT_REGISTERED("Your account isn't active or hasn't been approved yet."),
    NOT_AUTHORIZED("Your account not authorized.");

    private final String message;
}
