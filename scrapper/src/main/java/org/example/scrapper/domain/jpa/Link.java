package org.example.scrapper.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "link")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    @Id
    private Long id;

    private String link;

    @Column(name = "time_created")
    private Timestamp timeCreated;

    @Column(name = "time_checked")
    private Timestamp timeChecked;

    @Column(name = "updatedAt")
    private Timestamp updatedAt;

    @ManyToMany(mappedBy = "chat_link")
    private final Set<Chat> chats = new HashSet<>();
}
