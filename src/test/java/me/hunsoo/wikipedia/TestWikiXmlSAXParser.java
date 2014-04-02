package me.hunsoo.wikipedia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class TestWikiXmlSAXParser {
    WikiPage wikiPage;

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testParse() {
        String xmlPage = "<title>AccessibleComputing</title> <ns>0</ns> <id>10</id> <redirect title=\"Computer accessibility\" /> <revision> <id>381202555</id> <parentid>381200179</parentid> <timestamp>2010-08-26T22:38:36Z</timestamp> <contributor> <username>OlEnglish</username> <id>7181920</id> </contributor> <minor /> <comment>[[Help:Reverting|Reverted]] edits by [[Special:Contributions/76.28.186.133|76.28.186.133]] ([[User talk:76.28.186.133|talk]]) to last version by Gurch</comment> <text xml:space=\"preserve\">#REDIRECT [[Computer accessibility]] {{R from CamelCase}}</text> <sha1>lo15ponaybcg2sf49sstw9gdjmdetnk</sha1> <model>wikitext</model> <format>text/x-wiki</format> </revision>";

        try {
            wikiPage = WikiXmlSAXParser.parse(xmlPage);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(new Long(10), wikiPage.getDocumentId());
        assertEquals(new Long(381202555), wikiPage.getRevisionId());
        assertEquals(new Long(381200179), wikiPage.getParentId());
        assertEquals("AccessibleComputing",
                wikiPage.getTitle());
        assertEquals("#REDIRECT [[Computer accessibility]] {{R from CamelCase}}",
                wikiPage.getWikiText());
    }

    @Test
    public void testParse2() {
        String xmlPage = "<title>Eiffel</title> <ns>0</ns> <id>10000</id> <revision> <id>518988395</id> <parentid>510450663</parentid> <timestamp>2012-10-21T08:14:17Z</timestamp> <contributor> <username>AVB</username> <id>7558863</id> </contributor> <minor /> <comment>+ru-interwiki</comment> <text xml:space=\"preserve\">__NOTOC__ '''Eiffel''' may refer to: ==Engineering== * [[Eiffel Tower]] in Paris, designed by Gustave Eiffel in motif as twin to the Eiffel Bridge, re-dubbed Maria Pia Bridge, previously built in Porto, Portugal * [[Maria Pia Bridge]] in Porto, Portugal, designed and build by Gustave Eiffel - Preceding fraternal twin of Eiffel Tower in Paris, France * [[Eiffel Bridge, Ungheni]], Moldova, designed by Gustave Eiffel * [[Eiffel Bridge, Zrenjanin]], Serbia, build by Gustave Eiffel's company in Paris * [[Eiffel (company)]], successor of Gustave Eiffel's engineering company == Family name == * [[Gustave Eiffel]] (1832–1923), engineer and designer of the Eiffel Tower * [[Erika Eiffel]], an American woman who famously &quot;married&quot; the Eiffel Tower ==Entertainment== * [[Eiffel 65]], an electronic music group * &quot;[[Alec Eiffel]]&quot;, a song by the alternative rock band Pixies * [[Eiffel (band)]], a French rock group ==Computing== * [[Eiffel Software]], a software company * [[EiffelStudio]], a development environment for the Eiffel programming language * [[Eiffel (programming language)]], developed by Bertrand Meyer == See also == * [[Eifel]], region of Germany, origin of this surname * [[Jean Effel]] == References == {{Reflist}} {{disambiguation|surname|geo}} {{DEFAULTSORT:Eiffel}} [[Category:German toponyms]] [[Category:German-language surnames]] [[ar:إيفل (توضيح)]] [[bg:Айфел (пояснение)]] [[ca:Eiffel]] [[cs:Eiffel]] [[da:Eiffel]] [[de:Eiffel]] [[es:Eiffel]] [[fa:ایفل]] [[fr:Eiffel]] [[ko:에펠]] [[it:Eiffel]] [[he:אייפל]] [[lv:Eifelis]] [[lt:Eifelis (reikšmės)]] [[hu:Eiffel (egyértelműsítő lap)]] [[nl:Eiffel]] [[ja:エッフェル]] [[pl:Eiffel]] [[pt:Eiffel]] [[ro:Eiffel]] [[ru:Эйфель]] [[fi:Eiffel]] [[th:ไอเฟล]] [[vi:Eiffel]]</text> <sha1>nqqld2y58r106socf52a8mn9kh43mij</sha1> <model>wikitext</model> <format>text/x-wiki</format> </revision>";

        try {
            wikiPage = WikiXmlSAXParser.parse(xmlPage);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(new Long(10000), wikiPage.getDocumentId());
        assertEquals(new Long(518988395), wikiPage.getRevisionId());
        assertEquals(new Long(510450663), wikiPage.getParentId());
        assertEquals("Eiffel",
                wikiPage.getTitle());
        assertEquals("__NOTOC__ '''Eiffel''' may refer to: ==Engineering== * [[Eiffel Tower]] in Paris, designed by Gustave Eiffel in motif as twin to the Eiffel Bridge, re-dubbed Maria Pia Bridge, previously built in Porto, Portugal * [[Maria Pia Bridge]] in Porto, Portugal, designed and build by Gustave Eiffel - Preceding fraternal twin of Eiffel Tower in Paris, France * [[Eiffel Bridge, Ungheni]], Moldova, designed by Gustave Eiffel * [[Eiffel Bridge, Zrenjanin]], Serbia, build by Gustave Eiffel's company in Paris * [[Eiffel (company)]], successor of Gustave Eiffel's engineering company == Family name == * [[Gustave Eiffel]] (1832–1923), engineer and designer of the Eiffel Tower * [[Erika Eiffel]], an American woman who famously \"married\" the Eiffel Tower ==Entertainment== * [[Eiffel 65]], an electronic music group * \"[[Alec Eiffel]]\", a song by the alternative rock band Pixies * [[Eiffel (band)]], a French rock group ==Computing== * [[Eiffel Software]], a software company * [[EiffelStudio]], a development environment for the Eiffel programming language * [[Eiffel (programming language)]], developed by Bertrand Meyer == See also == * [[Eifel]], region of Germany, origin of this surname * [[Jean Effel]] == References == {{Reflist}} {{disambiguation|surname|geo}} {{DEFAULTSORT:Eiffel}} [[Category:German toponyms]] [[Category:German-language surnames]] [[ar:إيفل (توضيح)]] [[bg:Айфел (пояснение)]] [[ca:Eiffel]] [[cs:Eiffel]] [[da:Eiffel]] [[de:Eiffel]] [[es:Eiffel]] [[fa:ایفل]] [[fr:Eiffel]] [[ko:에펠]] [[it:Eiffel]] [[he:אייפל]] [[lv:Eifelis]] [[lt:Eifelis (reikšmės)]] [[hu:Eiffel (egyértelműsítő lap)]] [[nl:Eiffel]] [[ja:エッフェル]] [[pl:Eiffel]] [[pt:Eiffel]] [[ro:Eiffel]] [[ru:Эйфель]] [[fi:Eiffel]] [[th:ไอเฟล]] [[vi:Eiffel]]",
                wikiPage.getWikiText());
    }
}