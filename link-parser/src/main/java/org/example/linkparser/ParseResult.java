package org.example.linkparser;

public sealed interface ParseResult permits GithubParseResult, StackOverflowParseResult{
    String toString();
}
