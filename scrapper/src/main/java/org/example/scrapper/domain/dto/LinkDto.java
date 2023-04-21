package org.example.scrapper.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Class that represents Link entity in repo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkDto {
    /**
     * ID of link
     */
    private long id;
    /**
     * String value of link
     */
    private String link;
    /**
     * Time when link was created
     */
    private Timestamp timeCreated;
    /**
     * Time when link was updated
     */
    private Timestamp timeUpdated;
}
