package org.example.linkparser;

/**
 * Record for result of parsing GitHub link.
 * @param user Name of user account on GitHub
 * @param repo Repository name
 */
public record GithubParseResult(String user, String repo) implements ParseResult {
    public String toString() {
        return user + "/" + repo;
    }

    public String getLinkType() {
        return "github";
    }
}
