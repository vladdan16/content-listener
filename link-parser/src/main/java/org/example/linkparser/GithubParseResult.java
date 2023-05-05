package org.example.linkparser;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Record for result of parsing GitHub link.
 * @param user Name of user account on GitHub
 * @param repo Repository name
 */
public record GithubParseResult(String user, String repo) implements ParseResult {
    @Contract(pure = true) @Override
    public @NotNull String toString() {
        return user + "/" + repo;
    }

    @Contract(pure = true) @Override
    public @NotNull String getLinkType() {
        return "github";
    }
}
