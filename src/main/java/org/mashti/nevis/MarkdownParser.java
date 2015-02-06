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

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class MarkdownParser implements Parser {

    static final Pattern NEW_LINE = Pattern.compile("(\\n\\r|\\r|\u2424)");

    static final Processor[] BLOCK_PROCESSORS = {
            new NewLineProcessor(),
            new CodeBlockProcessor(),
            new ImageProcessor(),
            new DefinitionProcessor(),
            new BlockQuoteProcessor(),
            new HtmlTagProcessor(),
            new HeadingProcessor(),
            new HeadingSetextProcessor(),
            new HorizontalRuleProcessor(),
            new ListProcessor(),
            new ParagraphProcessor()
    };

    static final Processor[] INLINE_PROCESSORS = {
            new EscapeProcessor(),
            new HtmlInlineTagProcessor(),
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
            new TextProcessor()
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
        parseBlock(root, markdown);
        return root;
    }

    private void sanitise() {
        markdown = NEW_LINE.matcher(markdown).replaceAll("\n");
        markdown = UNICODE_WHITESPACE.matcher(markdown).replaceAll(" ");
        markdown = TAB.matcher(markdown).replaceAll("    ");
        markdown = Pattern.compile("^ *$", Pattern.MULTILINE).matcher(markdown).replaceAll("");
    }

    @Override
    public void parseBlock(final Node parent, String value) {

        while (!value.isEmpty()) {

            for (Processor processor : BLOCK_PROCESSORS) {

                final Pattern pattern = processor.pattern();
                final Matcher matcher = pattern.matcher(value);


                if (matcher.find()) {

                    if (matcher.start() != 0) {
                        throw new RuntimeException("block processor " + processor);
                    }

                    value = value.substring(matcher.end());

                    final Optional<Node> child = processor.process(matcher, this);
                    if (child.isPresent()) {
                        parent.addChild(child.get());
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void parseInline(final Node parent, String value) {

        while (!value.isEmpty()) {

            for (Processor processor : INLINE_PROCESSORS) {

                final Pattern pattern = processor.pattern();
                final Matcher matcher = pattern.matcher(value);


                if (matcher.find()) {

                    if (matcher.start() != 0) {
                        throw new RuntimeException("inline processor " + processor);
                    }

                    value = value.substring(matcher.end());

                    final Optional<Node> child = processor.process(matcher, this);
                    if (child.isPresent()) {
                        parent.addChild(child.get());
                    }
                    break;
                }
            }
        }
    }
}
