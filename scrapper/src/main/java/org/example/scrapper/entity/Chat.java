package org.example.scrapper.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    private Long id;

    @OneToMany(mappedBy = "owner")
    private List<Link> linkList;
}
