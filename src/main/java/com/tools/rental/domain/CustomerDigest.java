package com.tools.rental.domain;

import com.tools.rental.enumeration.CustomerStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDigest {
    private long id;
    private String firstName;
    private String lastName;
    private String phone;
    private CustomerStatus status;
    private LocalDate createDate;
}
