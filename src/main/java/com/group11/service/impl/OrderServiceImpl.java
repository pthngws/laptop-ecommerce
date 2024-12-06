package com.group11.service.impl;

import com.group11.dto.response.LineItemResponse;
import com.group11.dto.response.OrderResponse;
import com.group11.entity.OrderEntity;
import com.group11.repository.OrderRepository;
import com.group11.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Page<OrderResponse> getAllOrders(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderPage = orderRepository.findAll(pageable);

        return orderPage.map(this::toOrderResponse);
    }

    @Override
    public Page<OrderResponse> getAllOrdersByUserId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderPage = orderRepository.findAllOrderByUserUserID(id, pageable);
        return orderPage.map(this::toOrderResponse);
    }


    @Override
    public OrderResponse toOrderResponse(OrderEntity order) {
        List<LineItemResponse> items = order.getListLineItems().stream()
                .map(lineItem -> new LineItemResponse(
                        lineItem.getProduct().getName(),
                        lineItem.getProduct().getPrice(),
                        lineItem.getQuantity(),
                        lineItem.getProduct().getPrice() * lineItem.getQuantity()
                ))
                .toList();

        double totalAmount = items.stream()
                .mapToDouble(LineItemResponse::getSubtotal)
                .sum();

        return new OrderResponse(
                order.getOrderId(),
                order.getOrderDate(),
                order.getShippingStatus().name(),
                totalAmount,
                items
        );
    }

}
