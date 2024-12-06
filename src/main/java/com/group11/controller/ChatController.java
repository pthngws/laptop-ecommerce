package com.group11.controller;

import com.group11.entity.ChatEntity;
import com.group11.service.IChatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class ChatController {

    @Autowired
    private IChatService chatService;


    @GetMapping("/getCustomerList")
    public ResponseEntity<List<Map<String, Object>>> getCustomerList() {
        List<Map<String, Object>> customers = chatService.findDistinctSendersByReceiverId(); // receiverID = 1 (cố định hoặc từ token)
        return ResponseEntity.ok(customers);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public ChatEntity sendMessage(ChatEntity chatEntity) {
        Long senderID = chatEntity.getSenderID(); // Giả sử có cách lấy ID người gửi
        Long receiverID = chatEntity.getReceiverID();

        boolean isCustomerSendingToAdmin = isCustomer(senderID) && isAdmin(receiverID);
        boolean isAdminSendingToCustomer = isAdmin(senderID) && isCustomer(receiverID);

        if (!isCustomerSendingToAdmin && !isAdminSendingToCustomer) {
            throw new IllegalArgumentException("Quyền truy cập không hợp lệ");
        }

        chatEntity.setSentTime(new Date());
        chatService.save(chatEntity);
        return chatEntity;
    }

    // Ví dụ các phương thức kiểm tra vai trò người dùng
    private boolean isCustomer(Long userID) {
        return userID != 1;
    }

    private boolean isAdmin(Long userID) {
        return userID == 1;
    }


    @GetMapping("/getMessages")
    @ResponseBody
    public List<Map<String, Object>> loadMessage(
            @RequestParam Long senderId,
            @RequestParam Long receiverId) {
        return chatService.findAll().stream()
                .filter(row -> (row.getSenderID().equals(senderId) && row.getReceiverID().equals(receiverId)) ||
                        (row.getSenderID().equals(receiverId) && row.getReceiverID().equals(senderId)))
                .map(row -> {
                    Map<String, Object> messageMap = new HashMap<>();
                    messageMap.put("id", row.getChatID());
                    messageMap.put("contentMessage", row.getContentMessage());
                    messageMap.put("timestamp", row.getSentTime());
                    messageMap.put("senderID", row.getSenderID());
                    messageMap.put("receiverID", row.getReceiverID());
                    return messageMap;
                })
                .collect(Collectors.toList());
    }


}

