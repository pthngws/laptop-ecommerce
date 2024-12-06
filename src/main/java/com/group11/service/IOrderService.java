package com.group11.service;

import com.group11.dto.response.OrderResponse;
import com.group11.entity.OrderEntity;
import org.springframework.data.domain.Page;

public interface IOrderService {
    public OrderResponse toOrderResponse(OrderEntity order);
    public Page<OrderResponse> getAllOrders(int page, int size);
    public Page<OrderResponse> getAllOrdersByUserId(Long id, int page, int size);
}
