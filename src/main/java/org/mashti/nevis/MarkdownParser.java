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

import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.processor.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class MarkdownParser implements Parser {

    static final Pattern BEGINING = Pattern.compile("^");
    static final Pattern END = Pattern.compile("$");
    static final Pattern NEW_LINE = Pattern.compile("(\\n\\r|\\r|\u2424)");
    static final Pattern MARKDOWN_BLOCK = Pattern.compile("^\\s*$", Pattern.MULTILINE);

    private final Map<String, Link> link_references = new HashMap<String, Link>();

    static final Processor[] BLOCK_PROCESSORS = {
            new LinkReferenceProcessor(), new BlockQuoteProcessor(), new HtmlTagProcessor(), new HeadingProcessor(), new HeadingSetextProcessor(), new HorizontalRuleProcessor(), new ListProcessor(),
            new CodeBlockProcessor(),
            new ParagraphProcessor()
    };

    static final Processor[] INLINE_PROCESSORS = {
            new HtmlInlineTagProcessor(), new ListProcessor(), new CodeSpanProcessor(), new AutoLinkProcessor(), new InlineLinkProcessor(), new LinkProcessor(), new BackslashEscapeProcessor(), new BoldProcessor(), new EmphasizedProcessor(), new BreakLineProcessor(), new TextProcessor()
    };
    private static final Pattern TAB = Pattern.compile("\\t", Pattern.MULTILINE);
    private static final Pattern UNICODE_WHITESPACE = Pattern.compile("\u00a0", Pattern.MULTILINE);

    private String markdown;

    public MarkdownParser(String markdown) {

        this.markdown = markdown;
    }

    @Override
    public Node parse() {

        final Node root = new Node(null);
        sanitise();
        parseBlock(root, markdown);
        return root;
    }

    private void sanitise() {
        markdown = NEW_LINE.matcher(markdown).replaceAll("\n");
        markdown = TAB.matcher(markdown).replaceAll("    ");
        markdown = UNICODE_WHITESPACE.matcher(markdown).replaceAll(" ");
    }

    @Override
    public void parseBlock(final Node parent, final String value) {

        boolean matched = false;
        for (Processor processor : BLOCK_PROCESSORS) {

            final Pattern element = processor.pattern();
            final Matcher matcher = element.matcher(value);

            matched = matcher.find();
            if (matched) {

                String first_bit = value.substring(0, matcher.start());
                if (!first_bit.trim().isEmpty()) {
                    parseBlock(parent, first_bit);
                }

                processor.process(parent, matcher, this);

                String last_bit = value.substring(matcher.end());
                if (!last_bit.trim().isEmpty()) {
                    parseBlock(parent, last_bit);
                }

                break;
            }
        }
        if (!matched) {
            System.err.println("NO MATCH FOR BLOCK: " + value);
        }
    }

    @Override
    public void parseInline(final Node parent, final String value) {

        boolean matched = false;
        for (Processor processor : INLINE_PROCESSORS) {

            final Pattern element = processor.pattern();
            final Matcher matcher = element.matcher(value);

            matched = matcher.find();
            if (matched) {

                String first_bit = value.substring(0, matcher.start());
                if (!first_bit.isEmpty()) {
                    parseInline(parent, first_bit);
                }

                processor.process(parent, matcher, this);

                String last_bit = value.substring(matcher.end());
                if (!last_bit.isEmpty()) {
                    parseInline(parent, last_bit);
                }

                break;
            }
        }
        if (!matched) {
            System.err.println("NO MATCH FOR INLINE: " + value);
        }
    }

    @Override
    public Link referenceLink(final String id, final Link link) {

        return link_references.put(id, link);
    }

    @Override
    public Link getLinkReference(final String id) {

        final Link link = link_references.get(id);
        return link == null ? null : link.copy();
    }
}
