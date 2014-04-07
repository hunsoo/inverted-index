package me.hunsoo.wikipedia;

/**
 * Created by adward on 4/6/14.
 */
public class WikiXMLParser {

	public static WikiPage parse(String line) {
		WikiPage page = new WikiPage();

		int titleStartIndex = line.indexOf("<title>");
		int titleEndIndex = line.indexOf("</title>", titleStartIndex);
		String title = line.substring(titleStartIndex + 7, titleEndIndex);
		page.setTitle(title);

		int idStartIndex = line.indexOf("<id>", titleEndIndex);
		int idEndIndex = line.indexOf("</id>", idStartIndex);
		long id = Long.parseLong(line.substring(idStartIndex + 4, idEndIndex));
		page.setDocumentId(id);

		int textStartIndex = line.indexOf("<text", idEndIndex);
		int textStartClosingIndex = line.indexOf(">", textStartIndex + 5);
		int textEndIndex = line.indexOf("</text>", textStartClosingIndex);
		String text = line.substring(textStartClosingIndex + 1, textEndIndex);
		page.setWikiText(text);

		return page;
	}

}
