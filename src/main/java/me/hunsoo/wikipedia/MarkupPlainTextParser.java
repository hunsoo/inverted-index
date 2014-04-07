package me.hunsoo.wikipedia;

import org.eclipse.mylyn.wikitext.core.parser.Attributes;
import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.markup.MarkupLanguage;

import java.util.List;

/**
 * Created by adward on 4/4/14.
 */
public class MarkupPlainTextParser extends MarkupParser {

	private List<String> links;

	public MarkupPlainTextParser(MarkupLanguage markupLanguage) {
		super(markupLanguage);
	}

	public List<String> getLinks() {
		return links;
	}

	public String parseToPlainText(String markupContent) {
		setBuilder(new PlainTextDocumentBuilder());

		parse(markupContent);

		PlainTextDocumentBuilder builder = (PlainTextDocumentBuilder) getBuilder();
		String result = builder.getStringBuilder().toString();
		links = builder.getLinks();
		setBuilder(null);

		return result;
	}

	/**
	 * Parse only links in markupContent for faster processing.
	 * <p>
	 * Plain text in markupContent is not available even after parsing using this method.
	 * </p>
	 *
	 * @param markupContent
	 * @return list of linked documents' titles in markupContent
	 */
	public List<String> parseLinks(String markupContent) {
		setBuilder(new PlainTextDocumentBuilder() {
			@Override
			public void characters(String s) {
				// intentionally blank.
			}

			@Override
			public void endDocument() {
				// intentionally blank.
			}

			@Override
			public void link(Attributes attributes, String link, String linkText) {
				if (link.startsWith("/wiki/")) {
					addLink(link);
				}
			}
		});

		parse(markupContent);

		PlainTextDocumentBuilder builder = (PlainTextDocumentBuilder) getBuilder();
		links = builder.getLinks();
		setBuilder(null);

		return links;
	}
}
