package com.tools.rental.service;

import com.tools.rental.domain.CheckoutRequest;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.RentalAgreementDigest;
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

    public RentalAgreementDigest toolRentalCheckout(final CheckoutRequest request) throws InvalidRequestException {
        validate(request);

        final var rentalAgreementBuilder = RentalAgreementDigest.builder();
        return rentalAgreementBuilder.build();
    }

    private void validate(CheckoutRequest request) throws InvalidRequestException {
        if (request.rentalDayCount() < 1) {
            throw new InvalidRequestException("Must rent tool for at least one day");
        }
        if (request.discountPercent() < 0 || request.discountPercent() > 100) {
            throw new InvalidRequestException("Discount percent is not in the range 0 - 100%");
        }
        if (request.checkoutDate() == null) {
            throw new InvalidRequestException("Missing checkout date");
        }
        if (request.toolCode() == null) {
            throw new InvalidRequestException("Missing ToolCode");
        }
        validateStoreId(request.storeId());
    }

    private void validate(final CreateStoreToolTypeChargeRequest request) throws InvalidRequestException {
        validateStoreId(request.storeId());
        if (request.toolType() == null) {throw new InvalidRequestException("Missing ToolType");}
        if (request.dailyCharge() == null) {throw new InvalidRequestException("Missing daily charge");}

        int dollars = request.dailyCharge().intValue();
        if (dollars < 1) {throw new InvalidRequestException("Daily charge less than a dollar");}
    }

    private void validateStoreId(final Short storeId) throws InvalidRequestException {
        if (storeId == null) {throw new InvalidRequestException("Missing storeId");}
        if (storeId < 1) {throw new InvalidRequestException("Invalid storeId");}
    }
}
