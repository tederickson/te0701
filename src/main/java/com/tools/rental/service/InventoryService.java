package com.tools.rental.service;

import com.tools.rental.domain.CheckoutRequest;
import com.tools.rental.domain.CreateStoreToolTypeChargeRequest;
import com.tools.rental.domain.RentalAgreementDigest;
import com.tools.rental.domain.StoreToolTypeChargeDigest;
import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.enumeration.ToolType;
import com.tools.rental.exception.InvalidRequestException;
import com.tools.rental.exception.NotFoundException;
import com.tools.rental.mapper.StoreToolTypeChargeDigestMapper;
import com.tools.rental.model.StoreToolInventory;
import com.tools.rental.model.StoreToolRental;
import com.tools.rental.model.StoreToolTypeCharge;
import com.tools.rental.repository.StoreToolInventoryRepository;
import com.tools.rental.repository.StoreToolRentalRepository;
import com.tools.rental.repository.StoreToolTypeChargeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final StoreToolTypeChargeRepository storeToolTypeChargeRepository;
    private final StoreToolInventoryRepository storeToolInventoryRepository;
    private final StoreToolRentalRepository storeToolRentalRepository;

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

    public RentalAgreementDigest toolRentalCheckout(final CheckoutRequest request)
            throws InvalidRequestException, NotFoundException {
        validate(request);

        final ToolCode toolCode = request.toolCode();
        final var rentalAgreementBuilder = RentalAgreementDigest.builder()
                .withToolCode(toolCode)
                .withToolType(toolCode.getToolType())
                .withToolBrand(toolCode.getBrand())
                .withRentalDayCount(request.rentalDayCount())
                .withCheckoutDate(request.checkoutDate())
                .withDiscountPercent(request.discountPercent());

        LocalDate dueDate = request.checkoutDate().plusDays(request.rentalDayCount() - 1);
        rentalAgreementBuilder.withDueDate(dueDate);

        BigDecimal dailyCharge =
                storeToolTypeChargeRepository.findByStoreIdAndToolType(request.storeId(), toolCode.getToolType())
                        .map(StoreToolTypeCharge::getDailyCharge)
                        .orElseThrow(() -> new NotFoundException("toolType for store"));
        rentalAgreementBuilder.withDailyRentalCharge(dailyCharge);

        int maxAvailable = storeToolInventoryRepository.findByStoreIdAndToolCode(request.storeId(), toolCode)
                .map(StoreToolInventory::getMaxAvailable)
                .orElseThrow(() -> new NotFoundException("toolCode for store"));

        for (int day = 0; day < request.rentalDayCount(); day++) {
            LocalDate checkoutDate = request.checkoutDate().plusDays(day);
            int rentals = storeToolRentalRepository.findByStoreIdAndToolCodeAndCheckoutDate(request.storeId(),
                                                                                            toolCode,
                                                                                            checkoutDate)
                    .stream()
                    .mapToInt(StoreToolRental::getAmount)
                    .sum();
            if (rentals + 1 > maxAvailable) {
                throw new InvalidRequestException(rentals + " already checked out on " + checkoutDate);
            }

            StoreToolRental storeToolRental = new StoreToolRental()
                    .setToolCode(toolCode)
                    .setCheckoutDate(checkoutDate)
                    .setStoreId(request.storeId())
                    .setAmount((short) 1)
                    .setCustomerId(request.customerId());
            storeToolRentalRepository.save(storeToolRental);
        }

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
