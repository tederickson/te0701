package com.tools.rental.controller;

import com.tools.rental.domain.ChangePasswordRequest;
import com.tools.rental.domain.CreateCustomerRequest;
import com.tools.rental.domain.CustomerDigest;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.service.CustomerService;
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
@Tag(name = "Customer API")
@RequestMapping("v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Operation(summary = "Create Customer")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "201", description = "Successfully created customer"), //
            @ApiResponse(responseCode = "400", description = "Invalid customer values or customer already exists")})
    @PostMapping(produces = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public CustomerDigest createCustomer(@RequestBody final CreateCustomerRequest request) throws InvalidRequestException {
        return customerService.createCustomer(request);
    }

    @Operation(summary = "Get Customer ")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Get customer"), //
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    @GetMapping(value = "/{phone}", produces = "application/json")
    public CustomerDigest getCustomerByPhone(@PathVariable("phone") final String phone)
            throws InvalidRequestException, NotFoundException {
        return customerService.getCustomerByPhone(phone);
    }

    @Operation(summary = "Delete Customer")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Customer deleted")})
    @DeleteMapping(value = "/{phone}")
    public void deleteCustomer(@PathVariable("phone") final String phone) throws InvalidRequestException {
        customerService.deleteCustomerByPhone(phone);
    }

    @Operation(summary = "Change Password")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "200", description = "Successfully changed password"), //
            @ApiResponse(responseCode = "400", description = "Invalid password"), //
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    @PostMapping(value = "/{id}/change-password")
    public void changePassword(@PathVariable("id") long id, @RequestBody ChangePasswordRequest request)
            throws InvalidRequestException, NotFoundException {
        customerService.changePassword(id, request.password());
    }
}
