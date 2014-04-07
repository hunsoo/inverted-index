package me.hunsoo.wikipedia;

import org.eclipse.mylyn.wikitext.mediawiki.core.MediaWikiLanguage;

public class WikiTextParser {

	private static WikiTextParser self = null;
	private MarkupPlainTextParser parserImpl;

	private WikiTextParser() {
		parserImpl = new MarkupPlainTextParser(new MediaWikiLanguage());
	}

	public static WikiTextParser getInstance() {
		if (self == null) {
			self = new WikiTextParser();
		}

		return self;
	}

	public String parsePlainText(String wikiText) {
		wikiText = wikiText.replaceAll("<", "&lt;");
		wikiText = wikiText.replaceAll(">", "&gt;");

		return parserImpl.parseToPlainText(wikiText);
	}
}
