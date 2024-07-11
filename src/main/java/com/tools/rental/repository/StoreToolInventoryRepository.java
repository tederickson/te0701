package com.tools.rental.repository;

import com.tools.rental.enumeration.ToolCode;
import com.tools.rental.model.StoreToolInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreToolInventoryRepository extends JpaRepository<StoreToolInventory, Long> {
    Optional<StoreToolInventory> findByStoreIdAndToolCode(final short storeId, final ToolCode toolCode);
}
