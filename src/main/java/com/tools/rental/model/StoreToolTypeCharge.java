package com.tools.rental.model;

import com.tools.rental.enumeration.ToolType;
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
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
@Entity
public class StoreToolTypeCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private short storeId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToolType toolType;

    @Column(nullable = false)
    private BigDecimal dailyCharge;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoreToolTypeCharge storeToolTypeCharge = (StoreToolTypeCharge) o;
        return getId() == storeToolTypeCharge.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
