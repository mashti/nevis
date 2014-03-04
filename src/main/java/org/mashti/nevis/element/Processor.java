package org.mashti.nevis.element;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public abstract class Processor {

    private final Pattern pattern;

    protected Processor(Pattern pattern) {

        this.pattern = pattern;
    }

    public Pattern pattern() {

        return pattern;
    }

    public abstract void process(Node parent, Matcher matcher, Parser parser);

}
