/**
 * This file is part of nevis.
 *
 * nevis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * nevis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with nevis.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mashti.nevis.processor;

import org.apache.commons.lang.StringUtils;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.List;
import org.mashti.nevis.element.ListItem;
import org.mashti.nevis.element.Node;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class ListProcessor extends Processor {

    public ListProcessor() {

//        super(Pattern.compile("^" +
//                "(\\A|\\n{2,})" +
//                "(" +
//                "(" +
//                "[ ]{0," + 3 + "}" +
//                "((?:[-+*]|\\d+[.]))" + // $3 is first list item marker
//                "[ ]+" +
//                ")" +
//                "(?s:.+?)" +
//                "(" +
//                "\\z" + // End of input is OK
//                "|" +
//                "\\n{2,}" +
//                "(?=\\S)" + // If not end of input, then a new para
//                "(?![ ]*" +
//                "(?:[-+*]|\\d+[.])" +
//                "[ ]+" +
//                ")" + // negative look ahead for another list marker
//                ")" +
//                ")"));

        super(Pattern.compile("^( *)((?:[*+-]|\\d+\\.)) [\\s\\S]+?(?:\\n+(?=\\1?(?:[-*_] *){3,}(?:\\n+|$))|\\n+(?= *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$))|\\n{2,}(?! )(?!\\1(?:[*+-]|\\d+\\.) )\\n*|\\s*$)"));


    }

    @Override
    public Optional<Node> process(final Matcher matcher, Parser parser) {

        final boolean ordered = matcher.group(2).length() > 1;
        final String items = matcher.group();
        final List list = new List(ordered);

        processItems(list, items, parser);
        return Optional.of(list);
    }

    private void processItems(List parent, String items, Parser parser) {

        final Pattern p = Pattern.compile("^( *)((?:[*+-]|\\d+\\.)) [^\\n]*(?:\\n(?!\\1(?:[*+-]|\\d+\\.) )[^\\n]*)*", Pattern.MULTILINE);
        final Matcher matcher = p.matcher(items);

        final Pattern remove_bullets = Pattern.compile("^ *([*+-]|\\d+\\.) +");
        while (matcher.find()) {

            String item = matcher.group();
            item = remove_bullets.matcher(item).replaceAll("");
            System.out.println(item);

            final ListItem list_item = new ListItem();
            parser.parseBlock(list_item, item);
            
            
//            String leadingLine = matcher.group(1);
//            String split_item = matcher.group(4);
//            split_item = Pattern.compile("^[ ]{4,}", Pattern.MULTILINE).matcher(split_item).replaceAll("");
//
//            if (!(leadingLine == null || leadingLine.isEmpty()) || split_item.contains("\n\n")) {
//                parser.parseBlock(list_item, split_item);
//            } else {
//                parser.parseInline(list_item, split_item.trim());
//            }
            
            parent.addChild(list_item);
        }
    }
}
