package com.tools.rental.controller;

import com.tools.rental.client.RentalClient;
import com.tools.rental.domain.ToolCodeDigest;
import com.tools.rental.enumeration.Brand;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReferenceControllerIT {
    @LocalServerPort
    private int port;

    private RentalClient client;

    @BeforeEach
    void setUp() {
        client = new RentalClient("localhost", port);
    }

    @Test
    void getToolCodes() {
        List<ToolCodeDigest> toolCodes = client.getToolCodes();

        assertThat(toolCodes, hasSize(ToolCode.values().length));

        final var toolCode = toolCodes.stream().filter(x -> "LADW".equals(x.toolCode())).findFirst().orElseThrow();
        assertThat(toolCode.toolCode(), is(ToolCode.LADW.name()));

        final var toolType = ToolType.valueOf(toolCode.toolType());
        assertThat(toolType, is(ToolType.LADDER));

        final var brand = Brand.valueOf(toolCode.brand());
        assertThat(brand, is(Brand.WERNER));
    }
}