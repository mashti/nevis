package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Html;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HtmlTagProcessor extends Processor {

    public HtmlTagProcessor() {

        super(Pattern.compile("(" +
                "^<(" + "p|div|h1|h2|h3|h4|h5|h6|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del|span}" + ")" +
                "\\b" +
                "(.+\\n?)*" +
                "</\\2>" +
                "(\\n+|\\z))|" +

                "(?:" +
                "(?<=\\n\\n)" +
                "|" +
                "\\A\\n?" +
                ")" +
                "(" +
                "[ ]{0," + 3 + "}" +
                "<(hr)" +
                "\\b" +
                "([^<>])*?" +
                "/?>" +
                "[ ]*" +
                "(?=\\n{2,}|\\Z))" +
                "|" +
                "(?:" +
                "(?<=\\n\\n)" +
                "|" +
                "\\A\\n?" +
                ")" +
                "(" +
                "[ ]{0," + 3 + "}" +
                "(?s:" +
                "<!" +
                "(--.*?--\\s*)+" +
                ">" +
                ")" +
                "[ ]*" +
                "(?=\\n{2,}|\\Z)|" + "(" +
                "^" +
                "<(" + "p|div|h1|h2|h3|h4|h5|h6|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del" + ")" +
                "\\b" +
                "(.*\\n)*?" +
                ".*</\\2>" +
                "[ ]*" +
                "(?=\\n+|\\Z))" +
                ")", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final Html html = new Html(parent, Utils.removeStartAndEndNewLines(content));

        parent.addChild(html);
    }
}
