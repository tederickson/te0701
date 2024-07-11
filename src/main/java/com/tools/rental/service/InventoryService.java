package com.tools.rental.service;

import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.InvalidRequestException;
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

    public StoreToolTypeChargeDigest createStoreToolTypeCharge(final CreateStoreToolTypeChargeRequest request)
            throws InvalidRequestException {
        validate(request);

        if (storeToolTypeChargeRepository.findByStoreIdAndToolType(request.storeId(), request.toolType()).isPresent()) {
            throw new InvalidRequestException("charge already exists");
        }

        StoreToolTypeCharge storeToolTypeCharge = new StoreToolTypeCharge()
                .setStoreId(request.storeId())
                .setToolType(request.toolType())
                .setDailyCharge(request.dailyCharge());

        return StoreToolTypeChargeDigestMapper.map(storeToolTypeChargeRepository.save(storeToolTypeCharge));
    }

    public void deleteStoreToolTypeCharge(final Short storeId, final ToolType toolType) {
        final var storeToolTypeCharge = storeToolTypeChargeRepository.findByStoreIdAndToolType(storeId, toolType);

        storeToolTypeCharge.ifPresent(storeToolTypeChargeRepository::delete);
    }

    private void validate(final CreateStoreToolTypeChargeRequest request) throws InvalidRequestException {
        if (request.storeId() == null) {throw new InvalidRequestException("Missing storeId");}
        if (request.storeId() < 1) {throw new InvalidRequestException("Invalid storeId");}
        if (request.toolType() == null) {throw new InvalidRequestException("Missing ToolType");}
        if (request.dailyCharge() == null) {throw new InvalidRequestException("Missing daily charge");}

        int dollars = request.dailyCharge().intValue();
        if (dollars < 1) {throw new InvalidRequestException("Daily charge less than a dollar");}
    }


}
