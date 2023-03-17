package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowUrlHandler extends UrlHandler {
    private static final Pattern stackOverflowUrlPattern = Pattern.compile("^https://stackoverflow\\.com/questions/\\d+/.+$");

    public StackOverflowUrlHandler(UrlHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public ParseResult parseUrl(String url) {
        Matcher matcher = stackOverflowUrlPattern.matcher(url);
        if (matcher.matches()) {
            String[] list = url.split("/");
            return new StackOverflowParseResult(list[4]);
        } else {
            return next(url);
        }
    }
}
