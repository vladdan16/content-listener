package org.example.scrapper.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "chat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    private Long id;

    @Column(name = "time_created")
    private Timestamp timeCreated;

    @ManyToMany(mappedBy = "chat_link")
    private final Set<Link> links = new HashSet<>();
}
