package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.CodeBlock;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class CodeBlockProcessor extends Processor {

    private static final Pattern TABS = Pattern.compile("^(\\s{4}|\\t)");

    public CodeBlockProcessor() {

        super(Pattern.compile("" +
                "(?:\\n\\n|\\A)" +
                "((?:" +
                "(?:[ ]{4})" +
                ".*\\n+" +
                ")+" +
                ")" +
                "((?=^[ ]{0,4}\\S)|\\Z)", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        String value = Utils.replaceAllPerLine(TABS, matcher.group(1), "");

        final CodeBlock code = new CodeBlock(parent);
        code.addChild(new Text(code, Utils.removeStartAndEndNewLines(value)));
        parent.addChild(code);
    }
}
