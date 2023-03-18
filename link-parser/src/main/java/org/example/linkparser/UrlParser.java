package org.example.linkparser;

/**
 * A class to parse links
 * This class manages all handlers according Chain of Responsibility pattern
 */
public class UrlParser {
    /**
     * Chain of handlers that will be called
     */
    private UrlHandler chain;

    /**
     * Public constructor that calls build Chain method
     */
    public UrlParser() {
        buildChain();
    }

    /**
     * Private method to set chain of responsibilities
     * Here we can replace null with new handler if needed
     */
    private void buildChain() {
        chain = new GithubUrlHandler(new StackOverflowUrlHandler(null));
    }

    /**
     * Method to start parsing url from chain
     * @param url String url to parse
     * @return ParseResult instance
     */
    public ParseResult parseUrl(String url) {
        return chain.parseUrl(url);
    }
}
