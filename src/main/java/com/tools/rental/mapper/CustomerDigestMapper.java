package com.tools.rental.mapper;

import com.tools.rental.domain.CustomerDigest;
import com.tools.rental.model.Customer;

public final class CustomerDigestMapper {
    private CustomerDigestMapper() {
    }

    public static CustomerDigest map(final Customer customer) {
        return CustomerDigest.builder()
                .withCreateDate(customer.getCreateDate())
                .withId(customer.getId())
                .withFirstName(customer.getFirstName())
                .withLastName(customer.getLastName())
                .withPhone(customer.getPhone())
                .withStatus(customer.getStatus())
                .build();
    }
}
