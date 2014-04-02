package me.hunsoo.wikipedia;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestWikiTextParser {
    WikiTextParser parser;
    String wikiText;

    @Before
    public void setUp() {
        wikiText = "__NOTOC__ '''Eiffel''' may refer to: ==Engineering== * [[Eiffel Tower]] in Paris, designed by Gustave Eiffel in motif as twin to the Eiffel Bridge, re-dubbed Maria Pia Bridge, previously built in Porto, Portugal * [[Maria Pia Bridge]] in Porto, Portugal, designed and build by Gustave Eiffel - Preceding fraternal twin of Eiffel Tower in Paris, France * [[Eiffel Bridge, Ungheni]], Moldova, designed by Gustave Eiffel * [[Eiffel Bridge, Zrenjanin]], Serbia, build by Gustave Eiffel's company in Paris * [[Eiffel (company)]], successor of Gustave Eiffel's engineering company == Family name == * [[Gustave Eiffel]] (1832–1923), engineer and designer of the Eiffel Tower * [[Erika Eiffel]], an American woman who famously \"married\" the Eiffel Tower ==Entertainment== * [[Eiffel 65]], an electronic music group * \"[[Alec Eiffel]]\", a song by the alternative rock band Pixies * [[Eiffel (band)]], a French rock group ==Computing== * [[Eiffel Software]], a software company * [[EiffelStudio]], a development environment for the Eiffel programming language * [[Eiffel (programming language)]], developed by Bertrand Meyer == See also == * [[Eifel]], region of Germany, origin of this surname * [[Jean Effel]] == References == {{Reflist}} {{disambiguation|surname|geo}} {{DEFAULTSORT:Eiffel}} [[Category:German toponyms]] [[Category:German-language surnames]] [[ar:إيفل (توضيح)]] [[bg:Айфел (пояснение)]] [[ca:Eiffel]] [[cs:Eiffel]] [[da:Eiffel]] [[de:Eiffel]] [[es:Eiffel]] [[fa:ایفل]] [[fr:Eiffel]] [[ko:에펠]] [[it:Eiffel]] [[he:אייפל]] [[lv:Eifelis]] [[lt:Eifelis (reikšmės)]] [[hu:Eiffel (egyértelműsítő lap)]] [[nl:Eiffel]] [[ja:エッフェル]] [[pl:Eiffel]] [[pt:Eiffel]] [[ro:Eiffel]] [[ru:Эйфель]] [[fi:Eiffel]] [[th:ไอเฟล]] [[vi:Eiffel]]";
        parser = new WikiTextParser(wikiText);
    }

    @After
    public void tearDown() {

    }

/*
    @Test
    public void testInfoBox() {
        System.out.println("==InfoBox==");
        String infoBox = parser.getInfoBox().dumpRaw();
        System.out.println(infoBox == null ? "" :infoBox);
    }
*/

    @Test
    public void testLinks() {
        String expected[] = {
                "Eiffel Tower",
                "Maria Pia Bridge",
                "Eiffel Bridge, Ungheni",
                "Eiffel Bridge, Zrenjanin",
                "Eiffel (company)",
                "Gustave Eiffel",
                "Erika Eiffel",
                "Eiffel 65",
                "Alec Eiffel",
                "Eiffel (band)",
                "Eiffel Software",
                "EiffelStudio",
                "Eiffel (programming language)",
                "Eifel",
                "Jean Effel"
        };

        assertArrayEquals(expected, parser.getLinks().toArray());
    }

    @Test
    public void testCategories() {
        String expected[] = {
                "German toponyms",
                "German-language surnames"
        };

        assertArrayEquals(expected, parser.getCategories().toArray());
    }

    @Test
    public void testAttributes() {
        assertEquals("Eifelis", parser.getTranslatedTitle("lv"));
        assertEquals(false, parser.isDisambiguationPage());
        assertEquals(false, parser.isRedirect());
        assertEquals(false, parser.isStub());
        assertNull(parser.getRedirectText());
        parser.getInfoBox();
    }

    @Test
    @Ignore
    public void testPlainText() {
        String[] words = parser.getPlainText().split(" ");
        System.out.println("==PlainText==");
        for (String word : words) {
            word = word.trim();
            if (!word.equals("")) {
                System.out.println(word);
            }
        }
    }

    @Test
    public void testPlainText2() {
        parser = new WikiTextParser("#REDIRECT [[Computer accessibility]] {{R from CamelCase}}");
        String expected[] = {
                "Computer",
                "accessibility"
        };
        String[] words = parser.getPlainText().split(" ");
        System.out.println("==PlainText==");
        for (String word : words) {
            word = word.trim();
            if (!word.equals("")) {
                System.out.println(word);
            }
        }
        assertArrayEquals(expected, words);
    }

    @Test
    public void testPlainText3() {
        parser = new WikiTextParser("http://www.example.com/test_file.jpg https://www.a.net http://b.edu/index.html");
        assertEquals("", parser.getPlainText());
    }
}
