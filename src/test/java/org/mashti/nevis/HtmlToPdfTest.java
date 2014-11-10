package org.mashti.nevis;

import org.junit.Test;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author masih
 */
public class HtmlToPdfTest {

    @Test
    public void testPdfGeneration() throws Exception {
        final File inputFile = new File(HtmlToPdfTest.class.getResource("/html_to_pdf/printable.html").toURI());
        final String outputFile = "target/printable.pdf";
        OutputStream os = new FileOutputStream(outputFile, false);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(inputFile);
        renderer.layout();
        renderer.createPDF(os);

        os.close();

    }
}
