/**
 * Copyright © 2014, Masih H. Derkani
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.mashti.nevis;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.visitor.HtmlVisitor;
import org.mashti.nevis.visitor.Visitor;

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

            if (flavour_name.equals("Markdown103")) { //Markdown103
                getMarkdownFiles(flavour.toFile()).forEach(md_file -> {
                    params.add(new Object[]{flavour_name, md_file, getHtmlFile(md_file)});
                });
            }
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

        parser = new MarkdownParser();
    }

    @Test
    public void testParse() throws Exception {

        final Node node = parser.parse(FileUtils.readFileToString(md_file));
        final Visitor htmlVisitor = new HtmlVisitor();

        htmlVisitor.visit(node);
        Assert.assertEquals(FileUtils.readFileToString(html_file).trim(), htmlVisitor.toString().trim());
    }
}
