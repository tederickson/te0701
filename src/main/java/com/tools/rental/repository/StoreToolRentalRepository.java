package com.tools.rental.repository;

import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.model.StoreToolRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StoreToolRentalRepository extends JpaRepository<StoreToolRental, Long> {
    List<StoreToolRental> findByStoreIdAndToolCodeAndCheckoutDate(final short storeId,
                                                                  final ToolCode toolCode,
                                                                  final LocalDate checkoutDate);

    List<StoreToolRental> findByStoreId(final short storeId);
}
