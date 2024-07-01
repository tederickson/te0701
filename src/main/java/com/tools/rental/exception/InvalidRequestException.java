package com.tools.rental.exception;

public class InvalidRequestException extends Exception {
    public InvalidRequestException(RentalServiceError error) {
        super(error.getMessage());
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
