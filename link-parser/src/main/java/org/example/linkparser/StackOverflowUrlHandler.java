package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StackOverflowUrlHandler extends BaseUrlHandler {
    private static final Pattern stackOverflowUrlPattern = Pattern.compile("^https://stackoverflow\\.com/questions/\\d+/.+$");

    @Override
    public String parseUrl(String url) {
        Matcher matcher = stackOverflowUrlPattern.matcher(url);
        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return next(url);
        }
    }
}
