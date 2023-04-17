package org.example.scrapper.domain.Dao;

import org.example.scrapper.domain.dto.ChatDto;

import java.util.List;

public interface ChatDao {
    void add(ChatDto user);
    void remove(long chatId);
    List<ChatDto> findAll();
}
