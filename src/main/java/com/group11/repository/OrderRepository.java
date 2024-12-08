package com.group11.repository;

import com.group11.entity.OrderEntity;
import com.group11.entity.OrderShippingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Page<OrderEntity> findAllOrderByUserUserID(Long userId, Pageable pageable);
    @Query("SELECT o FROM OrderEntity o WHERE " +
            "(CAST(o.orderId AS string) LIKE %:keyword% OR " +
            "o.user.name LIKE %:keyword% OR " +
            "o.shippingStatus = :status)")
    Page<OrderEntity> searchByKeywordAndStatus(@Param("keyword") String keyword,
                                         @Param("status") OrderShippingStatus status,
                                         Pageable pageable);
    @Query("SELECT o FROM OrderEntity o WHERE o.shippingStatus = 'DELIVERIED' AND o.payment.paymentDate BETWEEN :startDate AND :endDate")
    List<OrderEntity> findOrdersWithShippingStatusAndReceiveDate(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);


}
