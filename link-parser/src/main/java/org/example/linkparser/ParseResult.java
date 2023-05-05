package org.example.linkparser;

/**
 * Interface that describes result of parsing.
 * I made it sealed so that I will be able to use switch case when I need to handle Results of parsing.
 */
public sealed interface ParseResult permits GithubParseResult, StackOverflowParseResult {
    /**
     * Method to translate result to human-readable string.
     * @return human-readable string
     */
    String toString();

    /**
     * Method to get type of link.
     * @return String type of link
     */
    String getLinkType();
}
