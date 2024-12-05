package com.group11.repository;

import com.group11.entity.RefundOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundOrderRepository extends JpaRepository<RefundOrderEntity, Long> {
}
