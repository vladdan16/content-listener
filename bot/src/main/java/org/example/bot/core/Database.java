package org.example.bot.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Temporary class that will be deleted when we connect real database. I used a singleton pattern for this database
 */
public class Database {
    private static Database database;
    private final Map<Long, User> users;

    private Database() {
        users = new HashMap<>();
    }

    public static Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public boolean registerUser(long id) {
        if (users.containsKey(id)) {
            return false;
        }
        users.put(id, new User(id));
        return true;
    }

    public boolean addLinkToUser(long id, String link) {
        return users.get(id).addLink(link);
    }

    public boolean removeLinkFromUser(long id, String link) {
        return users.get(id).removeLink(link);
    }

    public List<String> getAllLinksFromUser(long id) {
        return users.get(id).getLinks();
    }

    public boolean userExists(long id) {
        return users.containsKey(id);
    }
}
