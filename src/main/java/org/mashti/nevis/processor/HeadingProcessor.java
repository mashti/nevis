package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Heading;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HeadingProcessor extends Processor {

    public HeadingProcessor() {

        super(Pattern.compile("^(#{1,6})\\s*(.*?)\\s*\\1?$", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final int level = matcher.group(1).length();
        final String content = matcher.group(2);
        final Heading heading = new Heading(parent, level);

        heading.addChild(new Text(heading, content));
        parent.addChild(heading);
    }

}
