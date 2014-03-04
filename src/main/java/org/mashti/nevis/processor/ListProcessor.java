package org.mashti.nevis.processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.List;
import org.mashti.nevis.element.ListItem;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class ListProcessor extends Processor {

    public ListProcessor() {

        //        super(Pattern.compile("(\\A|\\n{2,})(([ ]{0,3}?([-+*]|\\d+\\.)[ ]+(.*?))+)", Pattern.DOTALL));
        super(Pattern.compile("^" +
//                "(\\A|\\n{2,})" +
                "(" +
                "(" +
                "[ ]{0," + 3 + "}" +
                "((?:[-+*]|\\d+[.]))" + // $3 is first list item marker
                "[ ]+" +
                ")" +
                "(?s:.+?)" +
                "(" +
                "\\z" + // End of input is OK
                "|" +
                "\\n{2,}" +
                "(?=\\S)" + // If not end of input, then a new para
                "(?![ ]*" +
                "(?:[-+*]|\\d+[.])" +
                "[ ]+" +
                ")" + // negative lookahead for another list marker
                ")" +
                ")", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String first_marker = matcher.group(3);
        String items = matcher.group(1);
        items = Pattern.compile("\\n{2,}").matcher(items).replaceAll("\n\n\n");
        final List list = new List(parent, !StringUtils.containsAny(first_marker, new char[] {'-', '+', '*'}));
       
        if(items.contains("Item 1, graf one.")){
            System.out.println();
        }
       
        processItems(list, items, parser);
        parent.addChild(list);
    }

    private void processItems(Node parent, String items, Parser parser) {

        items = Pattern.compile("\\n{2,}\\z").matcher(items).replaceAll("\n");

        Pattern p = Pattern.compile("(\\n*)?" +
                "^([ \\t]*)([-+*]|\\d+[.])[ ]+" +
                "((?s:.+?)(\\n{1,2}|\\z))" +
                "(?=\\n*(\\z|\\2([-+\\*]|\\d+[.])[ \\t]+))", Pattern.MULTILINE);

        final Matcher matcher = p.matcher(items);

        while (matcher.find()) {
            final ListItem list_item = new ListItem(parent);
            String leadingLine = matcher.group(1);
            String split_item = matcher.group(4);
            split_item = Pattern.compile("^[ ]{4,}", Pattern.MULTILINE).matcher(split_item).replaceAll("");

            if (!(leadingLine == null || leadingLine.isEmpty()) || split_item.contains("\n\n")) {
                parser.parseBlock(list_item, split_item);
            }
            else {
                parser.parseInline(list_item, split_item.trim());
            }
            parent.addChild(list_item);
        }
    }
}
