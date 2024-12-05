package com.group11.service.impl;

import com.group11.entity.ChatEntity;
import com.group11.repository.ChatRepository;
import com.group11.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Map<String, Object>> findDistinctSendersByReceiverId() {
        return chatRepository.findDistinctSendersByReceiverId();
    }


    @Override
    public ChatEntity save(ChatEntity chatEntity) {
        return chatRepository.save(chatEntity);
    }

    @Override
    public List<ChatEntity> findAll() {
        return chatRepository.findAll();
    }
}