package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Html;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HtmlInlineTagProcessor extends Processor {

    public HtmlInlineTagProcessor() {

        super(Pattern.compile("(" +
                "" +
                "<(" + "p|div|h1|h2|h3|h4|h5|h6|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del|span" + ")" +
                "\\b" +
                "(.*\\n)*?" +
                ".*</\\2>" +
                "[ ]*" +
                ")", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final String value = Utils.removeStartAndEndNewLines(content);
        final Html html = new Html(parent, value);
        parent.addChild(html);
    }
}
