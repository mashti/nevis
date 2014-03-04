package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class AutoLinkProcessor extends Processor {

    public AutoLinkProcessor() {

        super(Pattern.compile("<(" +
                "((https?|ftp):[^'\">\\s]+)" +
                "|" +
                "([-.\\w]+\\@[-a-z0-9]+(\\.[-a-z0-9]+)*\\.[a-z]+)" +
                ")>", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String destination = matcher.group(1);
        final Link link = new Link(parent, Utils.isEmail(destination) ? "mailto:" + destination : destination);
        parser.parseInline(link, destination);
        parent.addChild(link);
    }

}
