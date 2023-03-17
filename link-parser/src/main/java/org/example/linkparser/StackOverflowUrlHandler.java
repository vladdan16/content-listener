package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for StackOverflow links
 */
public final class StackOverflowUrlHandler extends UrlHandler {
    /**
     * Pattern for StackOverflow link
     */
    private final Pattern stackOverflowUrlPattern;

    /**
     * Child constructor that set a pattern for link
     * @param nextHandler Handler that will be called next
     */
    public StackOverflowUrlHandler(UrlHandler nextHandler) {
        super(nextHandler);
        stackOverflowUrlPattern = Pattern.compile("^https://stackoverflow\\.com/questions/\\d+/.+$");
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
