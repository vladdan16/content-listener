package org.example.linkparser;

/**
 * Abstract class that handle url to parse.
 */
public abstract class UrlHandler {
    /**
     * Next handler in chain.
     */
    private final UrlHandler nextHandler;

    /**
     * Public constructor that set next handler according to Chain of Responsibility pattern.
     * @param urlHandler Handler that will be called next
     */
    public UrlHandler(final UrlHandler urlHandler) {
        this.nextHandler = urlHandler;
    }

    /**
     * Calling nex handler.
     * @param url String url to parse
     * @return Instance of ParseResult
     */
    protected ParseResult next(final String url) {
        if (nextHandler != null) {
            return nextHandler.parseUrl(url);
        }
        return null;
    }

    /**
     * Abstract method that will be implemented child classes for specific type of link.
     * @param url String url to parse
     * @return Instance of ParseResult
     */
    public abstract ParseResult parseUrl(String url);
}
