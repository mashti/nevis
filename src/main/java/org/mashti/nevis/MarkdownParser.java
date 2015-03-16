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

import org.mashti.nevis.element.Node;
import org.mashti.nevis.processor.*;

import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class MarkdownParser implements Parser {

    static final Pattern NEW_LINE = Pattern.compile("(\\n\\r|\\r|\u2424)");

    static final Processor[] PROCESSORS = {
            new NewLineProcessor(),
            new CodeBlockProcessor(),
            new HeadingProcessor(),
            new HeadingSetextProcessor(),
            new HorizontalRuleProcessor(),
            new BlockQuoteProcessor(),
            new ListProcessor(),
            new HtmlTagProcessor(),
            new InlineHtmlTagProcessor(),
            new DefinitionProcessor(),
            new ImageProcessor(),
            new EscapeProcessor(),
            new ListProcessor(),
            new InlineCodeProcessor(),
            new AutoLinkProcessor(),
            new InlineLinkImageProcessor(),
            new InlineLinkProcessor(),
            new InlineLinkImageReferenceProcessor(),
            new LinkProcessor(),
            new StrongProcessor(),
            new EmphasizedProcessor(),
            new BreakLineProcessor(),
            new TextProcessor(),
            new ParagraphProcessor(),
    };

    private static final Pattern UNICODE_WHITESPACE = Pattern.compile("\u00a0", Pattern.MULTILINE);
    private static final Pattern TAB = Pattern.compile("\\t");


    private String markdown;

    public MarkdownParser(String markdown) {
        this.markdown = markdown;
    }

    @Override
    public Node parse() {

        final Node root = new Node();
        sanitise();
        parse(root, markdown);
        return root;
    }

    private void sanitise() {
        markdown = NEW_LINE.matcher(markdown).replaceAll("\n");
        markdown = UNICODE_WHITESPACE.matcher(markdown).replaceAll(" ");
        markdown = TAB.matcher(markdown).replaceAll("    ");
        markdown = Pattern.compile("^ *$", Pattern.MULTILINE).matcher(markdown).replaceAll("");
    }

    @Override
    public void parse(final Node parent, String value) {

        while (!value.isEmpty()) {

            for (Processor processor : PROCESSORS) {

                final String new_value = processor.process(parent, value, this);

                if (!value.equals(new_value)) {
                    value = new_value;
                    break;
                }
            }
        }
    }
}
