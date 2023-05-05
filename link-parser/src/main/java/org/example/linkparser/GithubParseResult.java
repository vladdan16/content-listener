package org.example.linkparser;

/**
 * Record for result of parsing GitHub link.
 * @param user Name of user account on GitHub
 * @param repo Repository name
 */
public record GithubParseResult(String user, String repo) implements ParseResult {
    @Override
    public String toString() {
        return user + "/" + repo;
    }
    @Override
    public String getLinkType() {
        return "github";
    }
}
