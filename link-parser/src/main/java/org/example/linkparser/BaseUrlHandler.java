package org.example.linkparser;

public abstract sealed class BaseUrlHandler implements UrlHandler permits GithubUrlHandler, StackOverflowUrlHandler {
    private UrlHandler nextHandler;

    @Override
    public UrlHandler setNextHandler(UrlHandler nextHandler) {
        this.nextHandler = nextHandler;
        return nextHandler;
    }

    protected String next(String url) {
        if (nextHandler != null) {
            return nextHandler.parseUrl(url);
        }
        return null;
    }
}
