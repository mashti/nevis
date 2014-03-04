package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.BreakLine;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BreakLineProcessor extends Processor {

    public BreakLineProcessor() {

        super(Pattern.compile(" {2,}\\n", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final BreakLine bold = new BreakLine(parent);
        parent.addChild(bold);
    }
}
