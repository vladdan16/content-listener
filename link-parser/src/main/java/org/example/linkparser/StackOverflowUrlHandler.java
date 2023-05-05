package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Handler for StackOverflow links.
 */
public final class StackOverflowUrlHandler extends UrlHandler {
    /**
     * Pattern for StackOverflow link.
     */
    private static final Pattern STACK_OVERFLOW_URL_PATTERN = Pattern.compile("^https://stackoverflow\\.com/questions/\\d+/.+$");
    private static final int QUESTION = 4;

    /**
     * Public constructor.
     * @param nextHandler Next handler in chain.
     */
    public StackOverflowUrlHandler(final UrlHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public ParseResult parseUrl(final String url) {
        Matcher matcher = STACK_OVERFLOW_URL_PATTERN.matcher(url);
        if (matcher.matches()) {
            String[] list = url.split("/");
            return new StackOverflowParseResult(list[QUESTION]);
        } else {
            return next(url);
        }
    }
}
