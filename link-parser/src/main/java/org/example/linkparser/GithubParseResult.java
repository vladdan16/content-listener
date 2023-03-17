package org.example.linkparser;

public final class GithubParseResult implements ParseResult {
    private final String user;
    private final String repo;

    public GithubParseResult(String user, String repo) {
        this.user = user;
        this.repo = repo;
    }

    @Override
    public String toString() {
        return user + "/" + repo;
    }

    public String getUser() {
        return user;
    }

    public String getRepo() {
        return repo;
    }
}
