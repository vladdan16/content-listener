package org.example.scrapper.domain.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT l FROM Link l WHERE l.link = :link")
    Link findLink(@Param("link") String link);

    @Query(" SELECT l FROM Link l WHERE l.timeChecked < :timestamp OR l.timeChecked IS NULL")
    List<Link> findAllByTimeCheckedBefore(@Param("timestamp") Timestamp timestamp);
}
