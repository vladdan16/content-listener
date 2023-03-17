package org.example.linkparser;

public record StackOverflowParseResult(String id) implements ParseResult {

    @Override
    public String toString() {
        return id;
    }
}
