package org.example.scrapper.domain.jdbc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * Class that represents Link entity in repo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    /**
     * ID of link.
     */
    private long id;
    /**
     * String value of link.
     */
    private String link;
    /**
     * Time when link was created.
     */
    private Timestamp timeCreated;
    /**
     * Time when link was checked.
     */
    private Timestamp timeChecked;
    /**
     * Time when content was updated.
     */
    private Timestamp updatedAt;
}
