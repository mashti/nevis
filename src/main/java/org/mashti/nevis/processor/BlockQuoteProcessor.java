package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.BlockQuote;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BlockQuoteProcessor extends Processor {

    public BlockQuoteProcessor() {

        super(Pattern.compile("(" +
                "(" +
                "^[ \t]*>[ \t]?" + // > at the start of a line
                ".+\\n" + // rest of the first line
                "(.+\\n)*" + // subsequent consecutive lines
                "\\n*" + // blanks
                ")+" +
                ")", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        String content = matcher.group(1);
        content = Pattern.compile("^[ \t]*>[ \t]?", Pattern.MULTILINE).matcher(content).replaceAll("");
        content = Pattern.compile("^[ \t]+$", Pattern.MULTILINE).matcher(content).replaceAll("");

        final BlockQuote block_quote = new BlockQuote(parent);
        if (!content.trim().isEmpty()) {
            parser.parseBlock(block_quote, content);
        }
        parent.addChild(block_quote);
    }

}
