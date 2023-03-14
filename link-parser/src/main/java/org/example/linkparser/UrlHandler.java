package org.example.linkparser;

public sealed interface UrlHandler permits BaseUrlHandler {
    String parseUrl(String url);
    UrlHandler setNextHandler(UrlHandler nextHandler);
}
