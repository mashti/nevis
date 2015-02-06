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
                final MarkdownParser parser = new MarkdownParser(FileUtils.readFileToString(file));
                final HtmlVisitor htmlVisitor = new HtmlVisitor();
                htmlVisitor.visit(parser.parse());
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
