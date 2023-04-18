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
    private static final Pattern GITHUB_URL_PATTERN = Pattern.compile("^https://github\\.com/[^/]+/[^/]+/?$");

    public GithubUrlHandler(UrlHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public ParseResult parseUrl(String url) {
        Matcher matcher = GITHUB_URL_PATTERN.matcher(url);
        if (matcher.matches()) {
            String[] list = url.split("/");
            return new GithubParseResult(list[3], list[4]);
        } else {
            return next(url);
        }
    }
}
