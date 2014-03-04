package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Paragraph;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class ParagraphProcessor extends Processor {

    public ParagraphProcessor() {

        super(Pattern.compile("(((.+(\\n)?)+)(\\n{2,}|\\z))", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group(2);
        final Paragraph paragraph = new Paragraph(parent);
        if (!content.trim().isEmpty()) {
            parser.parseInline(paragraph, Utils.removeStartAndEndNewLines(content)); }
        parent.addChild(paragraph);
    }

}
