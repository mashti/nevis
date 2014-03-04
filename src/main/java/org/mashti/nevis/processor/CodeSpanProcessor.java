package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Code;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class CodeSpanProcessor extends Processor {

    public CodeSpanProcessor() {

        super(Pattern.compile("(?<!\\\\)(`+)(.+?)(?<!`)\\1(?!`)"));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        String value = matcher.group(2);
        final Code code = new Code(parent);

        value = Pattern.compile("<").matcher(value).replaceAll("&lt;");
        value = Pattern.compile(">").matcher(value).replaceAll("&gt;");
        value = Pattern.compile("^ *").matcher(value).replaceAll("");
        value = Pattern.compile(" *$").matcher(value).replaceAll("");
        code.addChild(new Text(code, value));
        parent.addChild(code);
    }
}
