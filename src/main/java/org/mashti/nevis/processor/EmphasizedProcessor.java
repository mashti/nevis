package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Emphasized;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class EmphasizedProcessor extends Processor {

    public EmphasizedProcessor() {

        super(Pattern.compile("(\\*|_)(?=\\S)(.+?)(?<=\\S)\\1", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group(2);
        final Emphasized emphasized = new Emphasized(parent);
        parser.parseInline(emphasized, value);
        parent.addChild(emphasized);
    }

}
