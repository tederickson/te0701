package com.tools.rental.controller;

import com.tools.rental.client.RentalClient;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
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
    final private static short STORE_ID = 1;

    @LocalServerPort
    private int port;

    private RentalClient client;

    @BeforeEach
    void setUp() {
        client = new RentalClient("localhost", port);
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
}