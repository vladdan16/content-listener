package org.example.linkparser;

public record GithubParseResult(String user, String repo) implements ParseResult {

    @Override
    public String toString() {
        return user + "/" + repo;
    }
}
