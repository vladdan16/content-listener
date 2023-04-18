package org.example.linkparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UrlParserTest {

    @Test
    public void testValidGithubLink() {
        UrlParser urlParser = new UrlParser();

        String link = "https://github.com/vladdan16/content-listener";

        ParseResult result = urlParser.parseUrl(link);
        ParseResult expected = new GithubParseResult("vladdan16", "content-listener");

        assertEquals(result.toString(), expected.toString());
    }

    @Test
    public void testInvalidGithubLink() {
        UrlParser urlParser = new UrlParser();

        String link = "https://github.com/vladdan16/content-listener/something";

        ParseResult result = urlParser.parseUrl(link);
        assertNull(result);
    }

    @Test
    public void testValidStackoverflowLink() {
        UrlParser urlParser = new UrlParser();

        String link = "https://stackoverflow.com/questions/1642028/what-is-the-operator-in-c";

        ParseResult result = urlParser.parseUrl(link);
        ParseResult expected = new StackOverflowParseResult("1642028");

        assertEquals(result.toString(), expected.toString());
    }

    @Test
    public void testInvalidStackoverflowLink() {
        UrlParser urlParser = new UrlParser();

        String link = "https://stackoverflow.com/1642028/what-is-the-operator-in-c/asddas";

        ParseResult result = urlParser.parseUrl(link);
        assertNull(result);
    }

    @Test
    public void testInvalidLink() {
        UrlParser urlParser = new UrlParser();

        String link = "Any other content";

        ParseResult result = urlParser.parseUrl(link);
        assertNull(result);
    }
}
