package com.tools.rental.domain;

import com.tools.rental.enumeration.ToolType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder(setterPrefix = "with")
@NoArgsConstructor
@AllArgsConstructor
public final class StoreToolTypeChargeDigest {
    private Long id;
    private Short storeId;
    private ToolType toolType;
    private BigDecimal dailyCharge;
}
