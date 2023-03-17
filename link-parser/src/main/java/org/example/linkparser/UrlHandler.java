package org.example.linkparser;

/**
 * Abstract class
 */
public abstract class UrlHandler {
    private final UrlHandler nextHandler;

    public UrlHandler(UrlHandler nextHandler) {
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
