package com.tools.rental.service;

import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.mapper.StoreToolTypeChargeDigestMapper;
import com.tools.rental.model.StoreToolTypeCharge;
import com.tools.rental.repository.StoreToolTypeChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final StoreToolTypeChargeRepository storeToolTypeChargeRepository;

    public StoreToolTypeChargeDigest findByStoreIdAndToolType(final short storeId, final ToolType toolType)
            throws NotFoundException {
        StoreToolTypeCharge storeToolTypeCharge = storeToolTypeChargeRepository.findByStoreIdAndToolType(storeId,
                                                                                                         toolType)
                .orElseThrow(() -> new NotFoundException("toolType"));
        return StoreToolTypeChargeDigestMapper.map(storeToolTypeCharge);
    }
}
