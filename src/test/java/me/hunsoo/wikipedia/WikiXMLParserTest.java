package me.hunsoo.wikipedia;

import static org.junit.Assert.assertEquals;

import me.hunsoo.wikipedia.WikiPage;
import me.hunsoo.wikipedia.WikiXMLParser;
import org.junit.Test;

/**
 * Created by adward on 4/6/14.
 */
public class WikiXMLParserTest {
	@Test
	public void testParse() throws Exception {
		String testInput = "<title>AccessibleComputing</title> <ns>0</ns> <id>10</id> <redirect title=\"Computer accessibility\" /> <revision> <id>381202555</id> <parentid>381200179</parentid> <timestamp>2010-08-26T22:38:36Z</timestamp> <contributor> <username>OlEnglish</username> <id>7181920</id> </contributor> <minor /> <comment>[[Help:Reverting|Reverted]] edits by [[Special:Contributions/76.28.186.133|76.28.186.133]] ([[User talk:76.28.186.133|talk]]) to last version by Gurch</comment> <text xml:space=\"preserve\">#REDIRECT [[Computer accessibility]] {{R from CamelCase}}</text> <sha1>lo15ponaybcg2sf49sstw9gdjmdetnk</sha1> <models>wikitext</models> <format>text/x-wiki</format> </revision>";

		WikiPage page = WikiXMLParser.parse(testInput);

		assertEquals("AccessibleComputing", page.getTitle());
		assertEquals(new Long(10), page.getDocumentId());
		assertEquals("#REDIRECT [[Computer accessibility]] {{R from CamelCase}}", page.getWikiText());
	}
}
