package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.HorizontalRule;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HorizontalRuleProcessor extends Processor {

    public HorizontalRuleProcessor() {

        super(Pattern.compile("^[ ]{0,2}([ ]?(\\*|-|_)[ ]?){3,}[ ]*$", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        parent.addChild(new HorizontalRule(parent));
    }
}
