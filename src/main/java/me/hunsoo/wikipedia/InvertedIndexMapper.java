package me.hunsoo.wikipedia;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
	private Text word = new Text();
	private LongWritable docId;
	private Set<String> excludedWordSet;
	private Set<String> wordAtIdSet;
	private String[] tokens;

	private WikiPage wikiPage;

	private WikiTextParser wikiTextParser;

	public InvertedIndexMapper() {
		System.out.println("Init InvertedIndexMapper");
	}

	@Override
	protected void setup(Context context) throws IOException {
		URI[] caches = context.getCacheFiles();
		excludedWordSet = new HashSet<String>();
		if (caches != null) {
			setupExcludedWordList(caches[0].getPath());
		}

		wikiTextParser = WikiTextParser.getInstance();
		wordAtIdSet = new HashSet<String>();
	}

	@Override
	protected void cleanup(Context context) throws IOException, InterruptedException {
		for (String wordAtId : wordAtIdSet) {
			String wordAndId[] = wordAtId.split("@");
			word.set(wordAndId[0]);
			docId = new LongWritable(Long.parseLong(wordAndId[1]));
			context.write(word, docId);
		}
	}

	/**
	 * read excluded word list from distributed cache
	 *
	 * @param path
	 */
	private void setupExcludedWordList(String path) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(path));
			while (scanner.hasNext()) {
				String word = scanner.next();
				excludedWordSet.add(word.trim().toLowerCase());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Logger.getLogger(InvertedIndexMapper.class).info("Total " + excludedWordSet.size() + " words excluded.");
	}

	@Override
	protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
		String page = value.toString();
		wikiPage = WikiXMLParser.parse(page);
		String wikiText = wikiPage.getWikiText();
		long documentId = wikiPage.getDocumentId();
//		docId = new LongWritable(documentId);

		context.setStatus("docId " + documentId);

		String plainText = wikiTextParser.parsePlainText(wikiText);

		tokens = plainText.split(" ");

		for (int i = 0; i < tokens.length; ++i) {
			String token = tokens[i].trim().toLowerCase();

			if (!token.equals("") && !excludedWordSet.contains(token)) {
//				word.set(token);
//				context.write(word, docId);
				wordAtIdSet.add(token + "@" + documentId);
			}
		}
	}
}