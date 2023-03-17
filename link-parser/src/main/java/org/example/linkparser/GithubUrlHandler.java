package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GithubUrlHandler extends UrlHandler {
    private static final Pattern githubUrlPattern = Pattern.compile("^https://github\\.com/[^/]+/[^/]+/$");

    public GithubUrlHandler(UrlHandler nextHandler) {
        super(nextHandler);
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
