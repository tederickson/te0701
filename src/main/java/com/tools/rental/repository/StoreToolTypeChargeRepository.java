package com.tools.rental.repository;

import com.tools.rental.enumeration.ToolType;
import com.tools.rental.model.StoreToolTypeCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreToolTypeChargeRepository extends JpaRepository<StoreToolTypeCharge, Long> {
    Optional<StoreToolTypeCharge> findByStoreIdAndToolType(final short storeId, final ToolType toolType);
}
