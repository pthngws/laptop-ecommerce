package com.group11.service.impl;

import com.group11.dto.response.LineItemResponse;
import com.group11.dto.response.OrderResponse;
import com.group11.entity.OrderEntity;
import com.group11.repository.OrderRepository;
import com.group11.service.IOrderService;
import com.group11.entity.OrderShippingStatus;
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
    public OrderResponse getOrderById(Long id) {
        return orderRepository.findById(id).map(this::toOrderResponse).orElse(null);
    }

    @Override
    public Page<OrderResponse> getAllOrdersByUserId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderEntity> orderPage = orderRepository.findAllOrderByUserUserID(id, pageable);
        return orderPage.map(this::toOrderResponse);
    }

    @Override
    public Page<OrderResponse> searchOrders(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        OrderShippingStatus status = null;

        // Kiểm tra nếu keyword khớp với enum
        try {
            status = OrderShippingStatus.valueOf(keyword.toUpperCase());
        } catch (IllegalArgumentException ex) {
            // Nếu keyword không khớp enum, bỏ qua
        }

        Page<OrderEntity> orderPage = orderRepository.searchByKeywordAndStatus(keyword,status, pageable);

        return orderPage.map(this::toOrderResponse);
    }





    @Override
    public OrderResponse toOrderResponse(OrderEntity order) {
        List<LineItemResponse> items = order.getListLineItems().stream()
                .map(lineItem -> new LineItemResponse(
                        lineItem.getProduct().getName(),
                        lineItem.getProduct().getDetail().getImages().getFirst().getImageUrl(),
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
                order.getUser().getName(),
                order.getPhoneNumber(),
                order.getShippingAddress(),
                order.getOrderDate(),
                order.getReceiveDate(),
                order.getShippingStatus().name(),
                order.getPaymentStatus().name(),
                order.getPayment(),
                order.getNote(),
                totalAmount,
                items
        );
    }

}
