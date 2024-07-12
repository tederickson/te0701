package com.tools.rental.mapper;

import com.tools.rental.domain.RentalAgreementDigest;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalAgreementDigestMapperTest {
    private RentalAgreementDigest rentalAgreementDigest;

    @BeforeEach
    void setUp() {
        final LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        final LocalDate dueDate = LocalDate.of(2015, 10, 27);

        rentalAgreementDigest = RentalAgreementDigest.builder()
                .withToolCode(ToolCode.CHNS)
                .withToolType(ToolCode.CHNS.getToolType())
                .withToolBrand(ToolCode.CHNS.getBrand())
                .withRentalDayCount(5)
                .withCheckoutDate(checkoutDate)
                .withDueDate(dueDate)
                .withDailyRentalCharge(new BigDecimal("12341.49"))
                .withChargeDays(3)
                .withPreDiscountCharge(BigDecimal.ZERO)
                .withDiscountPercent(25)
                .withDiscountAmount(new BigDecimal("12341.12"))
                .withFinalCharge(new BigDecimal("12343.35"))
                .build();
    }

    @Test
    void missingToolCode() {
        rentalAgreementDigest.setToolCode(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing Tool Code"));
    }

    @Test
    void missingToolType() {
        rentalAgreementDigest.setToolType(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing Tool Type"));
    }

    @Test
    void missingBrand() {
        rentalAgreementDigest.setToolBrand(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing Brand"));
    }

    @Test
    void missingCheckoutDate() {
        rentalAgreementDigest.setCheckoutDate(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing checkout date"));
    }

    @Test
    void missingDueDate() {
        rentalAgreementDigest.setDueDate(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing due date"));
    }

    @Test
    void missingDailyRentalCharge() {
        rentalAgreementDigest.setDailyRentalCharge(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing daily rental charge"));
    }

    @Test
    void missingPreDiscountCharge() {
        rentalAgreementDigest.setPreDiscountCharge(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing pre-discount charge"));
    }

    @Test
    void missingDiscountAmount() {
        rentalAgreementDigest.setDiscountAmount(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing discount amount"));
    }

    @Test
    void missingFinalCharge() {
        rentalAgreementDigest.setFinalCharge(null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> RentalAgreementDigestMapper.toConsole(rentalAgreementDigest));
        assertThat(exception.getMessage(), is("Missing final charge"));
    }

    @Test
    void toConsole() throws InvalidRequestException {
        String console = RentalAgreementDigestMapper.toConsole(rentalAgreementDigest);

        System.out.println(console);
        assertThat(console, containsString("Check out date: 07/02/15"));
    }
}