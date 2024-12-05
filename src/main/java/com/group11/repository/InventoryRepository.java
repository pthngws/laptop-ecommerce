package com.group11.repository;

import com.group11.entity.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, Long> {

    @Query("SELECT i FROM InventoryEntity i WHERE i.product.name LIKE %:search%")
    Page<InventoryEntity> searchInventory(@Param("search") String search, Pageable pageable);

    // This can be used to get all inventory items with pagination, without filtering
    Page<InventoryEntity> findAll(Pageable pageable);
}
