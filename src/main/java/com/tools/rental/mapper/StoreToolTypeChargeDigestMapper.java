package com.tools.rental.mapper;

import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.model.StoreToolTypeCharge;

public final class StoreToolTypeChargeDigestMapper {
    private StoreToolTypeChargeDigestMapper() {
    }

    public static StoreToolTypeChargeDigest map(final StoreToolTypeCharge storeToolTypeCharge) {
        return StoreToolTypeChargeDigest.builder()
                .withId(storeToolTypeCharge.getId())
                .withStoreId(storeToolTypeCharge.getStoreId())
                .withToolType(storeToolTypeCharge.getToolType())
                .withDailyCharge(storeToolTypeCharge.getDailyCharge())
                .build();
    }
}
