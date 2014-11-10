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
