package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Escaped;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BackslashEscapeProcessor extends Processor {

    public BackslashEscapeProcessor() {

        super(Pattern.compile("(\\\\(<|>|_|`|!|#|\\*|\\{|\\\\|\\}|\\[|\\]|\\(|\\)|\\+|\\-|\\.))", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group(2);
        final Escaped bold = new Escaped(parent);
        parser.parseInline(bold, value);
        parent.addChild(bold);
    }
}
