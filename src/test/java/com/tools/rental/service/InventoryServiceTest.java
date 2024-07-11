package com.tools.rental.service;

import com.tools.rental.domain.CheckoutRequest;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.repository.StoreToolInventoryRepository;
import com.tools.rental.repository.StoreToolRentalRepository;
import com.tools.rental.repository.StoreToolTypeChargeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class InventoryServiceTest {
    private static final Short STORE_ID = 3;
    private static final LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        StoreToolTypeChargeRepository storeToolTypeChargeRepository = mock(StoreToolTypeChargeRepository.class);
        StoreToolInventoryRepository storeToolInventoryRepository = mock(StoreToolInventoryRepository.class);
        StoreToolRentalRepository storeToolRentalRepository = mock(StoreToolRentalRepository.class);

        inventoryService = new InventoryService(storeToolTypeChargeRepository,
                                                storeToolInventoryRepository,
                                                storeToolRentalRepository);
    }

    @Test
    void createStoreToolTypeCharge_missingStoreId() {
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest(null, ToolType.CHAINSAW,
                                                                                        BigDecimal.TEN);
        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.createStoreToolTypeCharge(request));

        assertThat(exception.getMessage(), is("Missing storeId"));
    }

    @Test
    void createStoreToolTypeCharge_invalidStoreId() {
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest((short) 0,
                                                                                        ToolType.CHAINSAW,
                                                                                        BigDecimal.TEN);
        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.createStoreToolTypeCharge(request));

        assertThat(exception.getMessage(), is("Invalid storeId"));
    }

    @Test
    void createStoreToolTypeCharge_missingToolType() {
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest(STORE_ID,
                                                                                        null,
                                                                                        BigDecimal.TEN);
        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.createStoreToolTypeCharge(request));

        assertThat(exception.getMessage(), is("Missing ToolType"));
    }

    @Test
    void createStoreToolTypeCharge_missingDailyCharge() {
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest(STORE_ID,
                                                                                        ToolType.JACKHAMMER,
                                                                                        null);
        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.createStoreToolTypeCharge(request));

        assertThat(exception.getMessage(), is("Missing daily charge"));
    }

    @Test
    void createStoreToolTypeCharge_invalidDailyCharge() {
        CreateStoreToolTypeChargeRequest request = new CreateStoreToolTypeChargeRequest(STORE_ID,
                                                                                        ToolType.JACKHAMMER,
                                                                                        new BigDecimal("0.99"));
        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.createStoreToolTypeCharge(request));

        assertThat(exception.getMessage(), is("Daily charge less than a dollar"));
    }

    @Test
    void toolRentalCheckout_invalidRentalDayCount() {
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, checkoutDate, 0, 0, STORE_ID, null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.toolRentalCheckout(request));

        assertThat(exception.getMessage(), is("Must rent tool for at least one day"));
    }

    @Test
    void toolRentalCheckout_invalidDiscount() {
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, checkoutDate, 1, -1, STORE_ID, null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.toolRentalCheckout(request));

        assertThat(exception.getMessage(), is("Discount percent is not in the range 0 - 100%"));
    }

    @Test
    void toolRentalCheckout_invalidCheckoutDate() {
        CheckoutRequest request = new CheckoutRequest(ToolCode.JAKR, null, 1, 0, STORE_ID, null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.toolRentalCheckout(request));

        assertThat(exception.getMessage(), is("Missing checkout date"));
    }

    @Test
    void toolRentalCheckout_invalidToolCode() {
        CheckoutRequest request = new CheckoutRequest(null, checkoutDate, 1, 0, STORE_ID, null);

        var exception = assertThrows(InvalidRequestException.class,
                                     () -> inventoryService.toolRentalCheckout(request));

        assertThat(exception.getMessage(), is("Missing ToolCode"));
    }
}