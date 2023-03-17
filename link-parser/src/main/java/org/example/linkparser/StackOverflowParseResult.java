package org.example.linkparser;

public final class StackOverflowParseResult implements ParseResult {
    private final String id;

    public StackOverflowParseResult(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return id;
    }

    public String getId() {
        return id;
    }
}
