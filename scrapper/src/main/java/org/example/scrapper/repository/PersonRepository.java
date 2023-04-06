package org.example.scrapper.repository;

import org.example.scrapper.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Chat, Long> {
}
