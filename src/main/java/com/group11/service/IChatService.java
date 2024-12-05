package com.group11.service;

import com.group11.entity.ChatEntity;

import java.util.List;
import java.util.Map;

public interface IChatService {
    List<Map<String, Object>> findDistinctSendersByReceiverId();

    ChatEntity save(ChatEntity chatEntity);

    List<ChatEntity> findAll();
}
