package com.tools.rental.domain;

import com.tools.rental.enumeration.Brand;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class RentalAgreementDigest {
    private ToolCode toolCode;
    private ToolType toolType;
    private Brand toolBrand;
    private int rentalDayCount;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private BigDecimal dailyRentalCharge;
    private int chargeDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;
}
