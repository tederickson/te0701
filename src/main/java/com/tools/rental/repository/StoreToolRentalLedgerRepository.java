package com.tools.rental.repository;

import com.tools.rental.model.StoreToolRentalLedger;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreToolRentalLedgerRepository extends JpaRepository<StoreToolRentalLedger, Long> {
    List<StoreToolRentalLedger> findAllByStoreIdAndCustomerIdOrderByCheckoutDateDesc(final short storeId,
                                                                                     final Long customerId,
                                                                                     final Pageable pageable);

    List<StoreToolRentalLedger> findAllByStoreId(final short storeId);
}
