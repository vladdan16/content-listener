package org.example.linkparser;

public abstract class BaseUrlHandler {
    private final BaseUrlHandler nextHandler;

    public BaseUrlHandler(BaseUrlHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    protected ParseResult next(String url) {
        if (nextHandler != null) {
            return nextHandler.parseUrl(url);
        }
        return null;
    }

    public abstract ParseResult parseUrl(String url);
}
