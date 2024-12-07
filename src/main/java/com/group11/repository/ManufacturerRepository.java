package com.group11.repository;

import com.group11.entity.ManufacturerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManufacturerRepository extends JpaRepository<ManufacturerEntity, Long> {
}
