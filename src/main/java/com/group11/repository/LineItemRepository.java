package com.group11.repository;

import com.group11.entity.LineItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineItemRepository extends JpaRepository<LineItemEntity, Long> {
}
