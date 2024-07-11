package com.tools.rental.domain;

import com.tools.rental.enumeration.ToolType;

import java.math.BigDecimal;

public record CreateStoreToolTypeChargeRequest(Short storeId, ToolType toolType, BigDecimal dailyCharge) {
}
