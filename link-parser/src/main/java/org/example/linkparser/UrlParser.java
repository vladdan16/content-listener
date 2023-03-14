package org.example.linkparser;

public class UrlParser {
    private final UrlHandler rootHandler;

    public UrlParser() {
        GithubUrlHandler githubHandler = new GithubUrlHandler();
        StackOverflowUrlHandler stackOverflowHandler = new StackOverflowUrlHandler();

        githubHandler.setNextHandler(stackOverflowHandler);

        rootHandler = githubHandler;
    }

    public String parseUrl(String url) {
        return rootHandler.parseUrl(url);
    }
}
