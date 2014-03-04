package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Bold;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BoldProcessor extends Processor {

    public BoldProcessor() {

        super(Pattern.compile("(\\*{2}|_{2})(?=\\S)(.+?[*_]*)(?<=\\S)\\1", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group(2);
        final Bold bold = new Bold(parent);
        parser.parseInline(bold, value);
        parent.addChild(bold);
    }
}
