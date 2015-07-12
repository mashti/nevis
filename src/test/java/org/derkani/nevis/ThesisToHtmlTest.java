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
package org.derkani.nevis;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.derkani.nevis.visitor.HtmlVisitor;

import java.io.File;
import java.io.IOException;

/**
 * @author masih
 */
public class ThesisToHtmlTest {

    @Test
    public void testGeneration() throws Exception {

        FileUtils.listFiles(new File("/Users/masih/Documents/PhD/Thesis/thesis/md"), new String[]{"md"}, false).forEach(file -> {
            try {
                final MarkdownParser parser = new MarkdownParser();
                final HtmlVisitor htmlVisitor = new HtmlVisitor();
                htmlVisitor.visit(parser.parse(FileUtils.readFileToString(file)));
                final String html = htmlVisitor.toString();
                final File thesis = new File("target/thesis");
                thesis.mkdirs();

                FileUtils.write(new File(thesis, file.getName() + ".html"), html);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
