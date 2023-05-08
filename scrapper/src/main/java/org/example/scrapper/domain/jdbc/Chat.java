package org.example.scrapper.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Class that represents Chat entity in repo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    /**
     * ID of telegram chat
     */
    private long id;

    /**
     * Time when chat was registered
     */
    private Timestamp timeCreated;
}
