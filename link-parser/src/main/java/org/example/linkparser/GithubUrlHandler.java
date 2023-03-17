package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for GitHub links
 */
public final class GithubUrlHandler extends UrlHandler {
    /**
     * Pattern for Github link
     */
    private final Pattern githubUrlPattern;

    /**
     * Child constructor that set a pattern for link
     * @param nextHandler Handler that will be called next
     */
    public GithubUrlHandler(UrlHandler nextHandler) {
        super(nextHandler);
        githubUrlPattern = Pattern.compile("^https://github\\.com/[^/]+/[^/]+/$");
    }

    @Override
    public ParseResult parseUrl(String url) {
        Matcher matcher = githubUrlPattern.matcher(url);
        if (matcher.matches()) {
            String[] list = url.split("/");
            return new GithubParseResult(list[3], list[4]);
        } else {
            return next(url);
        }
    }
}
