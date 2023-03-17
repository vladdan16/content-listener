package org.example.linkparser;

public abstract non-sealed class BaseUrlHandler implements UrlHandler {
    private UrlHandler nextHandler;

    @Override
    public void setNextHandler(UrlHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected String next(String url) {
        if (nextHandler != null) {
            return nextHandler.parseUrl(url);
        }
        return null;
    }
}
