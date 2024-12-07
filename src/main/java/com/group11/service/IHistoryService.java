package com.group11.service;

import com.group11.dto.response.CheckoutResponse;
import com.group11.entity.OrderEntity;
import com.group11.entity.UserEntity;

import java.util.List;

public interface IHistoryService {
    List<OrderEntity> getPurchaseHistory(Long userID);
    OrderEntity getOrderById(Long orderId);
    void cancelOrder(Long orderId, String accountNumber, String accountName,String bankName);
    UserEntity findUserByEmail(String email);
    CheckoutResponse getOrderDetails(Long orderId);

}
