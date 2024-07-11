package com.tools.rental.controller;

import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Inventory API")
@RequestMapping("v1/inventory")
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
        return inventoryService.findByStoreIdAndToolType(storeId, toolType);
    }

    @Operation(summary = "Create store ToolType charge")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "201", description = "Successfully created store ToolType charge"), //
            @ApiResponse(responseCode = "400", description = "Invalid store ToolType charge values or already exists")})
    @PostMapping(produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public StoreToolTypeChargeDigest createStoreToolTypeCharge(@RequestBody final CreateStoreToolTypeChargeRequest request)
            throws InvalidRequestException {
        return inventoryService.createStoreToolTypeCharge(request);
    }

    @Operation(summary = "Delete store ToolType charge")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "204", description = "Store ToolType charge deleted")})
    @DeleteMapping(value = "/stores/{storeId}/{toolType}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteStoreToolTypeCharge(@PathVariable("storeId") final Short storeId,
                                          @PathVariable("toolType") final ToolType toolType) {
        inventoryService.deleteStoreToolTypeCharge(storeId, toolType);
    }
}
