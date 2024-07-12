package com.tools.rental.model;

import com.tools.rental.enumeration.ToolCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@Entity
public class StoreToolRentalLedger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private short storeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToolCode toolCode;

    @Column(nullable = false)
    private LocalDate checkoutDate;

    @Column(nullable = false)
    private int rentalDayCount;

    @Column(nullable = false)
    private LocalDate dueDate;

    @Column(nullable = false)
    private BigDecimal dailyRentalCharge;

    @Column(nullable = false)
    private int chargeDays;

    @Column(nullable = false)
    private BigDecimal preDiscountCharge;

    @Column(nullable = false)
    private int discountPercent;

    @Column(nullable = false)
    private BigDecimal discountAmount;

    @Column(nullable = false)
    private BigDecimal finalCharge;

    @Column
    private Long customerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreToolRentalLedger storeToolRentalLedger = (StoreToolRentalLedger) o;
        return id == storeToolRentalLedger.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
