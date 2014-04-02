package me.hunsoo.wikipedia;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class InvertedIndexMapper extends Mapper<LongWritable, Text, Text, LongWritable> {
    private Text word = new Text();
    private LongWritable docId;
    private List<String> excludedWordList;
    private String[] tokens;

    private WikiPage wikiPage;

    public InvertedIndexMapper() {
        System.out.println("Init InvertedIndexMapper");
    }

    @Override
    protected void setup(Context context) throws IOException {
        URI[] caches = context.getCacheFiles();
        excludedWordList = new ArrayList<>();
        if (caches != null) {
            setupExcludedWordList(caches[0].getPath());
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
            while (scanner.hasNextLine()) {
                // assume the excluded words are delimited by a comma
                // ignore character case
                String line = scanner.nextLine();
                String[] words = line.split(",");
                for (String word : words) {
                    excludedWordList.add(word.trim().toLowerCase());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String page = value.toString();
        // assume each input page of interest has the entire contents of <page> tag, just not enclosed by one
        // and metadata tags outside <page> are discarded
        wikiPage = parseXml(page);
        String wikiText = wikiPage.getWikiText();
        docId = new LongWritable(wikiPage.getDocumentId());

        WikiTextParser wikiTextParser = new WikiTextParser(wikiText);
        String text = wikiTextParser.getPlainText();

        tokens = text.split(" ");

        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim().toLowerCase();

            if (!token.equals("") && !isExcludedWord(token)) {
                word.set(token);
                context.write(word, docId);
            }
        }
    }

    /**
     * produce an object representation of a wiki page
     *
     * @param xml well-formed xml representation of a wiki page
     */
    private WikiPage parseXml(String xml) {
        WikiPage wikiPage = null;
        try {
            wikiPage = WikiXmlSAXParser.parse(xml);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wikiPage;
    }

    private boolean isExcludedWord(String word) {
        // check if the word should be excluded
        boolean excluded = false;

        for (String excludedWord : excludedWordList) {
            if (excludedWord.equals(word)) {
                excluded = true;
                break;
            }
        }

        return excluded;
    }
}