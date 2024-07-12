package com.tools.rental.mapper;

import com.tools.rental.domain.RentalAgreementDigest;
import com.tools.rental.exception.InvalidRequestException;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RentalAgreementDigestMapper {

    private RentalAgreementDigestMapper() {
    }

    private static void validate(final RentalAgreementDigest rentalAgreementDigest) throws InvalidRequestException {
        if (rentalAgreementDigest.getToolCode() == null) {
            throw new InvalidRequestException("Missing Tool Code");
        }
        if (rentalAgreementDigest.getToolType() == null) {
            throw new InvalidRequestException("Missing Tool Type");
        }
        if (rentalAgreementDigest.getToolBrand() == null) {
            throw new InvalidRequestException("Missing Brand");
        }
        if (rentalAgreementDigest.getCheckoutDate() == null) {
            throw new InvalidRequestException("Missing checkout date");
        }
        if (rentalAgreementDigest.getDueDate() == null) {
            throw new InvalidRequestException("Missing due date");
        }
        if (rentalAgreementDigest.getDailyRentalCharge() == null) {
            throw new InvalidRequestException("Missing daily rental charge");
        }
        if (rentalAgreementDigest.getPreDiscountCharge() == null) {
            throw new InvalidRequestException("Missing pre-discount charge");
        }
        if (rentalAgreementDigest.getDiscountAmount() == null) {
            throw new InvalidRequestException("Missing discount amount");
        }
        if (rentalAgreementDigest.getFinalCharge() == null) {
            throw new InvalidRequestException("Missing final charge");
        }
    }

    public static String toConsole(final RentalAgreementDigest rentalAgreementDigest) throws InvalidRequestException {
        validate(rentalAgreementDigest);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try (PrintWriter buffer = new PrintWriter(byteArrayOutputStream)) {
            buffer.append("Tool code: ").println(rentalAgreementDigest.getToolCode().name());
            buffer.append("Tool type: ").println(rentalAgreementDigest.getToolType().name());
            buffer.append("Tool brand: ").println(rentalAgreementDigest.getToolBrand().name());
            buffer.append("Rental days: ").println(rentalAgreementDigest.getRentalDayCount());
            buffer.append("Check out date: ").println(format(rentalAgreementDigest.getCheckoutDate()));
            buffer.append("Due date: ").println(format(rentalAgreementDigest.getDueDate()));
            buffer.append("Daily rental charge: ").println(currency(rentalAgreementDigest.getDailyRentalCharge()));
            buffer.append("Charge days: ").println(rentalAgreementDigest.getChargeDays());
            buffer.append("Pre-discount charge: ").println(currency(rentalAgreementDigest.getPreDiscountCharge()));
            buffer.append("Discount percent: ").append(String.valueOf(rentalAgreementDigest.getDiscountPercent()))
                    .println("%");
            buffer.append("Discount amount: ").println(currency(rentalAgreementDigest.getDiscountAmount()));
            buffer.append("Final charge: ").println(currency(rentalAgreementDigest.getFinalCharge()));
        }
        return byteArrayOutputStream.toString();
    }

    private static String format(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        return formatter.format(date);
    }

    private static String currency(BigDecimal money) {
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.US);
        return currencyInstance.format(money.doubleValue());
    }
}
