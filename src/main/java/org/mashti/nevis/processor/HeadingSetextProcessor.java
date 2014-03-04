package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Heading;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HeadingSetextProcessor extends Processor {

    public HeadingSetextProcessor() {

        super(Pattern.compile("^(.+)\\n(-|=)+$", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String level_group = matcher.group(2);
        final int level = level_group.startsWith("-") ? 2 : 1;
        final String content = matcher.group(1);
        final Heading heading = new Heading(parent, level);

        heading.addChild(new Text(heading, content));
        parent.addChild(heading);

    }

}
