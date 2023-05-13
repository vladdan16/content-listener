package org.example.linkparser;

/**
 * Record for result of parsing StackOverflow link.
 * @param id question's id on StackOverflow
 */
public record StackOverflowParseResult(String id) implements ParseResult {

    @Override
    public String toString() {
        return id;
    }

    @Override
    public String getLinkType() {
        return "stackoverflow";
    }
}
