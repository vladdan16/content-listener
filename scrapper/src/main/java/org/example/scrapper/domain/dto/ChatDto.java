package org.example.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Class that represents Chat entity in repo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    /**
     * ID of telegram chat.
     */
    private long id;

    /**
     * Time when chat was registered.
     */
    private Timestamp timeCreated;
}
