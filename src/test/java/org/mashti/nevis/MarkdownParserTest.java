package org.mashti.nevis;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mashti.nevis.element.HtmlSerializer;
import org.mashti.nevis.element.Node;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
@RunWith(Parameterized.class)
public class MarkdownParserTest {

    private static final Collection<File> md_files = FileUtils.listFiles(new File("src/test/resources/pegdown_tests/MarkdownTest103"), new String[] {"md"}, false);
    private final File md_file;
    private final File html_file;
    private MarkdownParser parser;

    public MarkdownParserTest(File md_file, File html_file) {

        this.md_file = md_file;
        this.html_file = html_file;
    }

    @Parameterized.Parameters(name = "{index} {0}")
    public static Collection<Object[]> data() {

        final List<Object[]> params = new ArrayList<Object[]>();
        for (File md_file : md_files) {

            params.add(new Object[] {
                    md_file, new File(md_file.getParent(), FilenameUtils.getBaseName(md_file.getAbsolutePath()) + ".html")
            });
        }
        return params;
    }

    @Before
    public void setUp() throws Exception {

        parser = new MarkdownParser(FileUtils.readFileToString(md_file));
    }

    @Test
    public void testParse() throws Exception {

        if (md_file.getName().contains("Code Spans.md")) {
            System.out.println();
        }

        final Node node = parser.parse();
        final HtmlSerializer htmlSerializer = new HtmlSerializer(parser);
        htmlSerializer.visit(node);
        Assert.assertEquals(FileUtils.readFileToString(html_file), htmlSerializer.getHtml());
    }
}
