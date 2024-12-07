package com.group11.repository;

import com.group11.entity.ImageItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageItemRepository extends JpaRepository<ImageItemEntity,Long> {
}
