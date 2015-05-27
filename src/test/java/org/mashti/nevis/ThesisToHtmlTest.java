/**
 * This file is part of nevis.
 * <p>
 * nevis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * nevis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with nevis.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mashti.nevis;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mashti.nevis.visitor.HtmlVisitor;

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
