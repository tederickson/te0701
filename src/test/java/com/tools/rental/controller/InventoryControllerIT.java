package com.tools.rental.controller;

import com.tools.rental.client.RentalClient;
import com.tools.rental.domain.CheckoutRequest;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.RentalAgreementDigest;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.repository.StoreToolRentalRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryControllerIT {
    final private static short STORE_ID = 1;

    @LocalServerPort
    private int port;

    @Autowired
    private StoreToolRentalRepository storeToolRentalRepository;

    private RentalClient client;

    @BeforeEach
    void setUp() {
        client = new RentalClient("localhost", port);
    }

    @AfterEach
    void tearDown() {
        storeToolRentalRepository.deleteAll();
    }

    @Test
    void findByStoreIdAndToolType() {
        StoreToolTypeChargeDigest storeToolTypeChargeDigest = client.findByStoreIdAndToolType(STORE_ID,
                                                                                              ToolType.JACKHAMMER);

        assertThat(storeToolTypeChargeDigest, is(notNullValue()));
        assertThat(storeToolTypeChargeDigest.getStoreId(), is(STORE_ID));
        assertThat(storeToolTypeChargeDigest.getToolType(), is(ToolType.JACKHAMMER));
        assertThat(storeToolTypeChargeDigest.getDailyCharge(), is(new BigDecimal("2.99")));
    }

    @Test
    void findByStoreIdAndToolType_notFound() {
        short storeId = -1;
        var exception = assertThrows(HttpClientErrorException.NotFound.class,
                                     () -> client.findByStoreIdAndToolType(storeId, ToolType.LADDER));
        assertThat(exception.getMessage(), containsString("toolType"));
    }

    @Test
    void createStoreToolTypeCharge() {
        short storeId = 112;
        BigDecimal dailyCharge = new BigDecimal("12.99");
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest(storeId,
                                                                                        ToolType.JACKHAMMER,
                                                                                        dailyCharge);

        client.deleteStoreToolTypeCharge(storeId, ToolType.JACKHAMMER);

        StoreToolTypeChargeDigest storeToolTypeChargeDigest = client.createStoreToolTypeCharge(request);

        assertThat(storeToolTypeChargeDigest, is(notNullValue()));
        assertThat(storeToolTypeChargeDigest.getStoreId(), is(storeId));
        assertThat(storeToolTypeChargeDigest.getToolType(), is(ToolType.JACKHAMMER));
        assertThat(storeToolTypeChargeDigest.getDailyCharge(), is(dailyCharge));

        client.deleteStoreToolTypeCharge(storeId, ToolType.JACKHAMMER);
    }

    @Test
    void createStoreToolTypeCharge_alreadyExists() {
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest(STORE_ID,
                                                                                        ToolType.JACKHAMMER,
                                                                                        BigDecimal.TEN);
        var exception = assertThrows(HttpClientErrorException.BadRequest.class,
                                     () -> client.createStoreToolTypeCharge(request));
        assertThat(exception.getMessage(), containsString("charge already exists"));
    }

    @Test
    void toolRentalCheckout_storeIdNotFound() {
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        short storeId = 33;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");
        String dateString = checkoutDate.format(formatter);

        assertThat(dateString, is("9/3/15"));

        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, checkoutDate, 5, 0, storeId, null);
        var exception = assertThrows(HttpClientErrorException.NotFound.class,
                                     () -> client.toolRentalCheckout(request));
        assertThat(exception.getMessage(), containsString("toolType"));
    }

    @Test
    void toolRentalCheckout_maxRentalsNotFound() {
        short storeId = 21;
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, LocalDate.now(), 5, 0, storeId, null);
        var exception = assertThrows(HttpClientErrorException.NotFound.class,
                                     () -> client.toolRentalCheckout(request));
        assertThat(exception.getMessage(), containsString("toolCode for store"));
    }

    @Test
    void toolRentalCheckout_invalidDiscount() {  // Test 1
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, checkoutDate, 5, 101, STORE_ID, null);
        var exception = assertThrows(HttpClientErrorException.BadRequest.class,
                                     () -> client.toolRentalCheckout(request));
        assertThat(exception.getMessage(), containsString("Discount percent is not in the range 0 - 100%"));
    }

    @Test
    void toolRentalCheckout_julyHoliday() {  // Test 2
        LocalDate checkoutDate = LocalDate.of(2000, 7, 2);
        CheckoutRequest request = new CheckoutRequest(ToolCode.LADW, checkoutDate, 3, 10, STORE_ID, null);

        RentalAgreementDigest rentalAgreement = client.toolRentalCheckout(request);
        verify(request, rentalAgreement);

        LocalDate dueDate = LocalDate.of(2000, 7, 2 + 3 - 1);
        assertThat(rentalAgreement.getDueDate(), is(dueDate));
    }

    @Test
    void toolRentalCheckout_julyHolidayOnSaturday() {  // Test 3
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        CheckoutRequest request = new CheckoutRequest(ToolCode.CHNS, checkoutDate, 5, 25, STORE_ID, null);

        RentalAgreementDigest rentalAgreement = client.toolRentalCheckout(request);
        verify(request, rentalAgreement);
    }

    @Test
    void toolRentalCheckout_LaborDay() {  // Test 4
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKD, checkoutDate, 6, 0, STORE_ID, null);

        RentalAgreementDigest rentalAgreement = client.toolRentalCheckout(request);
        verify(request, rentalAgreement);
    }

    @Test
    void toolRentalCheckout_BeforeLaborDay() {  // Test 5
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, checkoutDate, 9, 0, STORE_ID, null);

        RentalAgreementDigest rentalAgreement = client.toolRentalCheckout(request);
        verify(request, rentalAgreement);
    }

    @Test
    void toolRentalCheckout_julyHoliday6() {  // Test 6
        LocalDate checkoutDate = LocalDate.of(2000, 7, 2);
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, checkoutDate, 4, 50, STORE_ID, null);

        RentalAgreementDigest rentalAgreement = client.toolRentalCheckout(request);
        verify(request, rentalAgreement);
    }

    private void verify(final CheckoutRequest request, final RentalAgreementDigest rentalAgreement) {
        ToolCode toolCode = request.toolCode();
        assertThat(rentalAgreement.getToolCode(), is(toolCode));
        assertThat(rentalAgreement.getToolType(), is(toolCode.getToolType()));
        assertThat(rentalAgreement.getToolBrand(), is(toolCode.getBrand()));
        assertThat(rentalAgreement.getRentalDayCount(), is(request.rentalDayCount()));
        assertThat(rentalAgreement.getCheckoutDate(), is(request.checkoutDate()));
        assertThat(rentalAgreement.getDiscountPercent(), is(request.discountPercent()));
    }
}