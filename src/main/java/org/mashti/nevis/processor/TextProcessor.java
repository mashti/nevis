package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class TextProcessor extends Processor {

    public TextProcessor() {

        super(Pattern.compile("((.*(\\n|\\z)?)+)", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group(1);
        final Text text = new Text(parent, value);
        parent.addChild(text);
    }
}
