package com.tools.rental.service;

import com.tools.rental.domain.CreateCustomerRequest;
import com.tools.rental.domain.CustomerDigest;
import com.tools.rental.enumeration.CustomerStatus;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.exception.RentalServiceError;
import com.tools.rental.mapper.CustomerDigestMapper;
import com.tools.rental.model.Customer;
import com.tools.rental.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerDigest createCustomer(final CreateCustomerRequest request) throws InvalidRequestException {
        validate(request);

        if (customerRepository.findByPhone(request.phone()).isPresent()) {
            throw new InvalidRequestException(RentalServiceError.EXISTING_USER);
        }

        final Customer customer = new Customer()
                .setFirstName(request.firstName())
                .setLastName(request.lastName())
                .setPhone(request.phone())
                .setEmail(request.email())
                .setCreateDate(LocalDate.now())
                .setStatus(CustomerStatus.NEW)
                .setPassword("customer needs to change");

        return CustomerDigestMapper.map(customerRepository.save(customer));
    }

    private void validate(CreateCustomerRequest request) throws InvalidRequestException {
        if (StringUtils.isBlank(request.phone())) {throw new InvalidRequestException("Missing phone");}
        if (request.phone().length() != 10) {throw new InvalidRequestException("Phone format");}
        if (StringUtils.isBlank(request.firstName())) {throw new InvalidRequestException("Missing first name");}
        if (StringUtils.isBlank(request.lastName())) {throw new InvalidRequestException("Missing last name");}
    }

    public CustomerDigest getCustomerByPhone(final String phone) throws InvalidRequestException, NotFoundException {
        if (StringUtils.isBlank(phone)) {throw new InvalidRequestException("Missing phone");}

        final var customer = customerRepository.findByPhone(phone).orElseThrow(() -> new NotFoundException("customer"));
        return CustomerDigestMapper.map(customer);
    }

    public void deleteCustomerByPhone(final String phone) throws InvalidRequestException {
        if (StringUtils.isBlank(phone)) {throw new InvalidRequestException("Missing phone");}

        final var customer = customerRepository.findByPhone(phone);

        customer.ifPresent(customerRepository::delete);
    }

    public void changePassword(final long id, final String password) throws InvalidRequestException, NotFoundException {
        if (PasswordValidator.isNotValid(password)) {
            throw new InvalidRequestException(RentalServiceError.INVALID_PASSWORD);
        }

        final var customer = customerRepository.findById(id).orElseThrow(() -> new NotFoundException("customer"));
        final var encodedPassword = PasswordEncodeDecode.encode(password);

        customer.setPassword(encodedPassword)
                .setStatus(CustomerStatus.ACTIVE);
        customerRepository.save(customer);
    }
}
