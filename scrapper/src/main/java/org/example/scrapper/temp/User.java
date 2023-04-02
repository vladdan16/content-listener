package org.example.scrapper.temp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Temporary record until we connect database
 * @param id Long user id
 */
public record User(long id) {
    private static Set<String> links;

    public boolean addLink(String link) {
        if (links == null) {
            links = new HashSet<>();
        }
        if (links.contains(link)) {
            return false;
        }
        links.add(link);
        return true;
    }

    public boolean removeLink(String link) {
        if (links == null) {
            return false;
        }
        if (!links.contains(link)) {
            return false;
        }
        links.remove(link);
        return true;
    }

    public List<String> getLinks() {
        return links.stream().toList();
    }
}
