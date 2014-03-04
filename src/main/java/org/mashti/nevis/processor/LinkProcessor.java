package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class LinkProcessor extends Processor {

    public LinkProcessor() {

        super(Pattern.compile("(?m)(\\[(.*?)\\][ ]?(?:\\n[ ]*)?(\\[(.*?)\\])?)", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        String id = matcher.group(4);
        final String text = matcher.group(2);

        final Link link = new Link(parent, null);
        if (id == null || id.trim().isEmpty()) {
            id = text;
        }
        link.setId(id);
        link.addChild(new Text(link, text));

        parent.addChild(link);
    }
}
