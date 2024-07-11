package com.tools.rental.domain;

import com.tools.rental.enumeration.ToolCode;

import java.time.LocalDate;

public record CheckoutRequest(ToolCode toolCode,
                              LocalDate checkoutDate,
                              int rentalDayCount,
                              int discountPercent,
                              Short storeId,
                              Long customerId) {
}
