package org.example.linkparser;

public sealed interface UrlHandler permits BaseUrlHandler {
    String parseUrl(String url);
    void setNextHandler(UrlHandler nextHandler);
}
