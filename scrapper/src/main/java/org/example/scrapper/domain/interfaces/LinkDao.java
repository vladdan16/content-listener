package org.example.scrapper.domain.interfaces;

import org.example.scrapper.domain.dto.LinkDto;

import java.util.List;

public interface LinkDao {
    void add(LinkDto link);
    void remove(String link);
    List<LinkDto> findAll();
}
