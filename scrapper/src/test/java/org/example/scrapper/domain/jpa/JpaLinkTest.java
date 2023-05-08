package org.example.scrapper.domain.jpa;

import org.example.scrapper.IntegrationEnvironment;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class JpaLinkTest extends IntegrationEnvironment {
    @Autowired
    private LinkRepository linkRepository;

    @Test
    @Transactional
    @Rollback
    public void addTest() {
        Link link = new Link();
        link.setLink("stackoverflow.com");
        link = linkRepository.save(link);
        List<Link> links = linkRepository.findAll();
        assertEquals(links.size(), 2);
    }

    @Test
    @Transactional
    @Rollback
    public void removeTest() {
        Link link = new Link();
        link.setLink("stackoverflow.com");
        link = linkRepository.save(link);
        linkRepository.delete(link);
        List<Link> links = linkRepository.findAll();
        assertEquals(links.size(), 1);
    }

    @Test
    @Transactional
    public void findAllTest() {
        List<Link> links = linkRepository.findAll();
        assertEquals(1, links.size());
    }
}
