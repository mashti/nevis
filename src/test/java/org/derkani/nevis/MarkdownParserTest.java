/*
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
package org.derkani.nevis;

import org.apache.commons.io.*;
import org.derkani.nevis.element.Node;
import org.derkani.nevis.visitor.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import static org.junit.Assert.*;

/**
 * @author Masih Hajiarab Derkani
 */
@RunWith(Parameterized.class)
public class MarkdownParserTest {

    private static final URL FLAVOURS_HOME = MarkdownParserTest.class.getResource("/flavours");
    private static final String HTML = "html";
    private static final String HTM = "htm";
    private static final String XHTML = "xhtml";
    private static final String YAML = "yaml";
    private static final String YML = "yml";
    private static final String MD = "md";

    private String flavourName;
    private final File input;
    private final File expected;
    private final MarkdownParser parser;
    private final Visitor visitor;

    public MarkdownParserTest(final String flavourName, final File input, File expected) {

        this.flavourName = flavourName;
        this.input = input;
        this.expected = expected;
        parser = new MarkdownParser();
        visitor = getVisitor();
    }

    @Parameterized.Parameters(name = "{index} {0}: {1}")
    public static Collection<Object[]> data() throws URISyntaxException, IOException {

        final List<Object[]> params = new ArrayList<>();
        final Path flavours = Paths.get(FLAVOURS_HOME.toURI());
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

        return FileUtils.listFiles(flavour_directory, new String[]{MD}, true);
    }

    @Test
    public void visitedInputIsAsExpected() throws Exception {

        final String inputString = FileUtils.readFileToString(input);
        final Node node = parser.parse(inputString);
        visitor.visit(node);

        final String expectedOutput = FileUtils.readFileToString(this.expected).trim();
        final String actualOutput = visitor.toString().trim();

        assertEquals(expectedOutput, actualOutput);
    }

    private Visitor getVisitor() {

        final String extension = FilenameUtils.getExtension(expected.getName());
        switch (extension) {
            case HTML:
            case HTM:
            case XHTML:
                return new HtmlVisitor();
            case YAML:
            case YML:
                return new YamlVisitor();
            default:
                throw new RuntimeException("visitor not known for extension " + extension);
        }
    }
}
