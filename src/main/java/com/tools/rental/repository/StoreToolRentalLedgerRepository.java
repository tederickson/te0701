package com.tools.rental.repository;

import com.tools.rental.model.StoreToolRentalLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StoreToolRentalLedgerRepository extends JpaRepository<StoreToolRentalLedger, Long> {
    List<StoreToolRentalLedger> findAllByStoreIdAndCheckoutDateBetween(final short storeId,
                                                                       final LocalDate startDate,
                                                                       final LocalDate endDate);
}
