package com.tools.rental.service;

import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.repository.StoreToolTypeChargeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class InventoryServiceTest {
    private static final Short STORE_ID = 3;

    private StoreToolTypeChargeRepository storeToolTypeChargeRepository;
    private InventoryService inventoryService;

    @BeforeEach
    void setUp() {
        storeToolTypeChargeRepository = mock(StoreToolTypeChargeRepository.class);
        inventoryService = new InventoryService(storeToolTypeChargeRepository);
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
}