package com.tools.rental.controller;

import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Inventory API")
@RequestMapping("v1/inventory")
@Slf4j
public class InventoryController {
    private final InventoryService inventoryService;

    @Operation(summary = "Find a store ToolType charge by using StoreId and ToolType")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get store ToolType charge"), //
            @ApiResponse(responseCode = "404", description = "store ToolType charge not found")})
    @GetMapping(value = "/stores/{storeId}/{toolType}", produces = "application/json")
    public StoreToolTypeChargeDigest findByStoreIdAndToolType(@PathVariable("storeId") final Short storeId,
                                                              @PathVariable("toolType") final ToolType toolType)
            throws NotFoundException {
        log.info("storeId = {}, toolType = {}", storeId, toolType);
        return inventoryService.findByStoreIdAndToolType(storeId, toolType);
    }
}
