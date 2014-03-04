package org.mashti.nevis;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.processor.AutoLinkProcessor;
import org.mashti.nevis.processor.BackslashEscapeProcessor;
import org.mashti.nevis.processor.BlockQuoteProcessor;
import org.mashti.nevis.processor.BoldProcessor;
import org.mashti.nevis.processor.BreakLineProcessor;
import org.mashti.nevis.processor.CodeBlockProcessor;
import org.mashti.nevis.processor.CodeSpanProcessor;
import org.mashti.nevis.processor.EmphasizedProcessor;
import org.mashti.nevis.processor.HeadingProcessor;
import org.mashti.nevis.processor.HeadingSetextProcessor;
import org.mashti.nevis.processor.HorizontalRuleProcessor;
import org.mashti.nevis.processor.HtmlInlineTagProcessor;
import org.mashti.nevis.processor.HtmlTagProcessor;
import org.mashti.nevis.processor.InlineLinkProcessor;
import org.mashti.nevis.processor.LinkProcessor;
import org.mashti.nevis.processor.LinkReferenceProcessor;
import org.mashti.nevis.processor.ListProcessor;
import org.mashti.nevis.processor.ParagraphProcessor;
import org.mashti.nevis.processor.TextProcessor;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class MarkdownParser implements Parser {

    static final Pattern BEGINING = Pattern.compile("^");
    static final Pattern END = Pattern.compile("$");
    static final Pattern NEW_LINE = Pattern.compile("(\\n\\r|\\n|\\r)");
    static final Pattern MARKDOWN_BLOCK = Pattern.compile("^\\s*$", Pattern.MULTILINE);

    private final Map<String, Link> link_references = new HashMap<String, Link>();

    static final Processor[] BLOCK_PROCESSORS = {
            new LinkReferenceProcessor(), new BlockQuoteProcessor(), new HtmlTagProcessor(), new HeadingProcessor(), new HeadingSetextProcessor(), new HorizontalRuleProcessor(), new ListProcessor(), 
            new CodeBlockProcessor(), 
            new ParagraphProcessor()
    };

    static final Processor[] INLINE_PROCESSORS = {
            new HtmlInlineTagProcessor(), new ListProcessor(), new CodeSpanProcessor(), new AutoLinkProcessor(), new BackslashEscapeProcessor(), new InlineLinkProcessor(), new LinkProcessor(), new BoldProcessor(), new EmphasizedProcessor(), new BreakLineProcessor(), new TextProcessor()
    };
    private static final Pattern TAB = Pattern.compile("\\t", Pattern.MULTILINE);

    private String markdown;

    public MarkdownParser(String markdown) {

        this.markdown = markdown;
    }

    @Override
    public Node parse() {

        final Node root = new Node(null);

        markdown = NEW_LINE.matcher(markdown).replaceAll("\n");
        markdown = TAB.matcher(markdown).replaceAll("    ");
        parseBlock(root, markdown);
        for (String s : link_references.keySet()) {
            System.out.println("ID> s" + s);
        }
        return root;
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
