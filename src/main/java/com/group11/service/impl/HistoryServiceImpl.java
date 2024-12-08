package com.group11.service.impl;

import com.group11.dto.EmailDetail;
import com.group11.dto.request.LineItemRequest;
import com.group11.dto.response.CheckoutResponse;
import com.group11.entity.OrderEntity;
import com.group11.entity.OrderShippingStatus;
import com.group11.entity.RefundOrderEntity;
import com.group11.entity.UserEntity;
import com.group11.repository.HistoryRepository;
import com.group11.repository.RefundOrderRepository;
import com.group11.repository.OrderRepository;
import com.group11.repository.UserRepository;
import com.group11.service.IEmailService;
import com.group11.service.IHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements IHistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RefundOrderRepository refundOrderRepository;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<OrderEntity> getPurchaseHistory(Long userID) {
        return historyRepository.getHistory(userID);
    }

    @Override
    public OrderEntity getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public void cancelOrder(Long orderId, String accountNumber, String accountName, String bankName) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng với ID: " + orderId));

        RefundOrderEntity refundOrder = new RefundOrderEntity();
        refundOrder.setOrderId(orderId);
        refundOrder.setAccountName(accountName);
        refundOrder.setBankName(bankName);
        refundOrder.setAccountNumber(accountNumber);

        refundOrderRepository.save(refundOrder);



        order.setShippingStatus(OrderShippingStatus.valueOf("CANCELED"));
        orderRepository.save(order);

        EmailDetail emailDetail = new EmailDetail();

        String body = "Chào " + order.getUser().getName() + ",\n\n" +
                "Chúng tôi thông báo rằng đơn hàng của bạn (Mã đơn hàng: " + orderId +
                ") đã hủy.\n\n" +
                "Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.\n\n" +
                "Trân trọng,\n";
        emailDetail.setMsgBody(body);
        emailDetail.setRecipient(order.getUser().getEmail());
        emailDetail.setSubject("Thông báo hủy đơn hàng");
        emailService.sendEmailConfirmCancelOrder(emailDetail);

    }

    @Override
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public CheckoutResponse getOrderDetails(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<LineItemRequest> cartItems = order.getListLineItems().stream()
                .map(item -> new LineItemRequest(
                        item.getProduct().getProductID(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice(),
                        item.getQuantity() * item.getProduct().getPrice()
                )).toList();

        return new CheckoutResponse(
                order.getUser().getName(),
                order.getUser().getEmail(),
                order.getPhoneNumber(),  // Lấy số điện thoại từ UserEntity
                order.getNote(),
                order.getShippingAddress(),
                cartItems,
                cartItems.stream().mapToInt(item -> (int) item.getTotal()).sum(),
                order.getOrderId(),  // Mã đơn hàng
                order.getOrderDate(),  // Ngày đặt
                order.getShippingStatus(),  // Trạng thái giao hàng
                order.getPaymentStatus()  // Trạng thái thanh toán
        );
    }



}
