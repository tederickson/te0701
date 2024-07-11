package com.tools.rental.controller;

import com.tools.rental.client.RentalClient;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryControllerIT {
    @LocalServerPort
    private int port;

    private RentalClient client;

    @BeforeEach
    void setUp() {
        client = new RentalClient("localhost", port);
    }


    @Test
    void findByStoreIdAndToolType() {
        short storeId = 1;
        StoreToolTypeChargeDigest storeToolTypeChargeDigest = client.findByStoreIdAndToolType(storeId,
                                                                                              ToolType.JACKHAMMER);

        assertThat(storeToolTypeChargeDigest, is(notNullValue()));
        assertThat(storeToolTypeChargeDigest.getStoreId(), is(storeId));
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
}