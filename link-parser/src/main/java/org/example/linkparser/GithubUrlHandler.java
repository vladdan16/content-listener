package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for GitHub links.
 */
public final class GithubUrlHandler extends UrlHandler {
    /**
     * Pattern for Github link.
     */
    private static final Pattern GITHUB_URL_PATTERN = Pattern.compile("^https://github\\.com/[^/]+/[^/]+/?$");
    private static final int USER = 3;
    private static final int REPO = 4;

    /**
     * Public constructor.
     * @param nextHandler handler
     */
    public GithubUrlHandler(final UrlHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public ParseResult parseUrl(final String url) {
        Matcher matcher = GITHUB_URL_PATTERN.matcher(url);
        if (matcher.matches()) {
            String[] list = url.split("/");
            return new GithubParseResult(list[USER], list[REPO]);
        } else {
            return next(url);
        }
    }
}
