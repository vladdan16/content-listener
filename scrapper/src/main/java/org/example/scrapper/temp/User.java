package org.example.scrapper.temp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Temporary class until we connect database
 */
public class User {

    private final long id;
    private final Set<String> links;

    public User(long id) {
        this.id = id;
        links = new HashSet<>();
    }

    public boolean addLink(String link) {
        if (links.contains(link)) {
            return false;
        }
        links.add(link);
        return true;
    }

    public boolean removeLink(String link) {
        if (!links.contains(link)) {
            return false;
        }
        links.remove(link);
        return true;
    }

    public List<String> getLinks() {
        return links.stream().toList();
    }

    public long getId() {
        return id;
    }
}
