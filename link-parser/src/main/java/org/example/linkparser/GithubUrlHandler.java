package org.example.linkparser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GithubUrlHandler extends BaseUrlHandler {
    private static final Pattern githubUrlPattern = Pattern.compile("^https://github\\.com/[^/]+/[^/]+/$");

    @Override
    public String parseUrl(String url) {
        Matcher matcher = githubUrlPattern.matcher(url);
        if (matcher.matches()) {
            return matcher.group(1) + "/" + matcher.group(2);
        } else {
            return next(url);
        }
    }
}
