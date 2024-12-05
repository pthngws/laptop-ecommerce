package com.group11.service;

import com.group11.entity.InventoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IInventoryService {
    Page<InventoryEntity> getInventory(String search, Pageable pageable);

    Optional<InventoryEntity> findById(Long id);
    InventoryEntity save(InventoryEntity inventoryEntity);
}
