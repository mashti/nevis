/**
 * This file is part of nevis.
 *
 * nevis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * nevis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with nevis.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mashti.nevis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mashti.nevis.element.HtmlSerializer;
import org.mashti.nevis.element.Node;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
@RunWith(Parameterized.class)
public class MarkdownParserTest {

    private String flavour;
    private final File md_file;
    private final File html_file;
    private MarkdownParser parser;

    public MarkdownParserTest(String flavour, File md_file, File html_file) {

        this.flavour = flavour;
        this.md_file = md_file;
        this.html_file = html_file;
    }

    @Parameterized.Parameters(name = "{index} {0}: {1}")
    public static Collection<Object[]> data() throws URISyntaxException, IOException {

        final List<Object[]> params = new ArrayList<>();

        final Path flavours = Paths.get(MarkdownParserTest.class.getResource("/flavours").toURI());

        final Stream<Path> list = Files.list(flavours);

        list.forEach(flavour -> {

            final String flavour_name = flavour.getFileName().toString();

            getMarkdownFiles(flavour.toFile()).forEach(md_file -> {
                params.add(new Object[]{flavour_name, md_file, getHtmlFile(md_file)});
            });

        });
        return params;
    }

    private static File getHtmlFile(File md_file) {
        return new File(md_file.getParent(), FilenameUtils.getBaseName(md_file.getAbsolutePath()) + ".html");
    }

    private static Collection<File> getMarkdownFiles(File flavour_directory) {
        return FileUtils.listFiles(flavour_directory, new String[]{"md"}, true);
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
