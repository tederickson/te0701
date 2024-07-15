package com.tools.rental.controller;

import com.tools.rental.domain.CheckoutRequest;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.RentalAgreementDigest;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.mapper.RentalAgreementDigestMapper;
import com.tools.rental.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    @ResponseStatus(code = HttpStatus.OK)
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Store ToolType charge deleted")})
    @DeleteMapping(value = "/stores/{storeId}/{toolType}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteStoreToolTypeCharge(@PathVariable("storeId") final Short storeId,
                                          @PathVariable("toolType") final ToolType toolType) {
        inventoryService.deleteStoreToolTypeCharge(storeId, toolType);
    }

    @Operation(summary = "Checkout tool rental")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "201", description = "Successfully created tool rental"), //
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "store ToolType charge not found")})
    @PostMapping(value = "/checkout", produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RentalAgreementDigest toolRentalCheckout(@RequestBody final CheckoutRequest request)
            throws InvalidRequestException, NotFoundException {
        return inventoryService.toolRentalCheckout(request);
    }

    @Operation(summary = "Convert checkout tool rental to formatted console message")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Successfully created tool rental"), //
            @ApiResponse(responseCode = "400", description = "Invalid request")})
    @PostMapping(value = "/checkout:export", produces = "text/plain")
    @ResponseStatus(code = HttpStatus.OK)
    public String toolRentalCheckoutExportToConsole(@RequestBody final RentalAgreementDigest request)
            throws InvalidRequestException {
        return RentalAgreementDigestMapper.toConsole(request);
    }

    @Operation(summary = "Display a history of most recent transactions by using StoreId and CustomerId")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get matching transactions"), //
            @ApiResponse(responseCode = "404", description = "invalid store or customer id")})
    @GetMapping(value = "/stores/{storeId}/customers/{customerId}/pageNo/{pageNo}/pageSize/{pageSize}",
            produces = "application/json")
    @ResponseStatus(code = HttpStatus.OK)
    public List<RentalAgreementDigest> findByStoreIdAndCustomerId(@PathVariable("storeId") final Short storeId,
                                                                  @PathVariable("customerId") final long customerId,
                                                                  @PathVariable("pageNo") final int pageNo,
                                                                  @PathVariable("pageSize") final int pageSize)
            throws InvalidRequestException {
        final Pageable pageable = PageRequest.of(pageNo, pageSize);
        return inventoryService.findByStoreIdAndCustomerId(storeId, customerId, pageable);
    }
}
