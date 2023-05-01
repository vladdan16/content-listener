package org.example.scrapper.domain.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan("org.example.scrapper.domain.jpa.*")
@EnableJpaRepositories
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
