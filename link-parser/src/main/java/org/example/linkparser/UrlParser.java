package org.example.linkparser;

public class UrlParser {
    private BaseUrlHandler chain;

    public UrlParser() {
        buildChain();
    }

    private void buildChain() {
        chain = new GithubUrlHandler(new StackOverflowUrlHandler(null));
    }

    public ParseResult parseUrl(String url) {
        return chain.parseUrl(url);
    }
}
