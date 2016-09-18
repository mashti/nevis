/**
 * Copyright © 2014, Masih H. Derkani
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.derkani.nevis.processor;

import org.derkani.nevis.Parser;
import org.derkani.nevis.element.List;
import org.derkani.nevis.element.ListItem;
import org.derkani.nevis.element.Node;
import org.derkani.nevis.element.Paragraph;
import ru.lanwen.verbalregex.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarab Derkani
 */
public class ListProcessor extends Processor {

    private static final String BULLET = "(?:[\\*+-]|(?:\\d+\\.))";

    public ListProcessor() {

//        super(Pattern.compile("
// ^( *)(" + BULLET + ") [\\s\\S]+?
// (?:\\n+(?=\\1?(?:[-*_] *){3,}(?:\\n+|$))|
// \\n+(?= *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$))|
// \\n{2,}(?! )(?!\\1" + BULLET + " )\\n*|\\s*$)"));
        super(VerbalExpression.regex()
                        .searchOneLine(true)
                        .startOfLine()
                        .lineBreak().zeroOrMore()
                        .capture()
                            .capture()
                              .then(" ").count(0, 3)
                              .endCapture()
                            .capture().add(BULLET).endCapture()
//                        .capture().anyOf("*+-").endCapture()
                            .anyOf(" \\t").oneOrMore()
                            .capture()
                              .something().lineBreak()
//                              .anything().lineBreak()
                        
//                              .add("[\\s\\S]+?")
                            .endCapture()//.oneOrMore()//.add("?")
                        
                        .endCapture().oneOrMore()//.add("?")
                              .add( 
                             "(?=" 
//                                   + "\\2?" 
//                                   + "([-\\*_] *){3,})" 
//                             + "|(\\n{2,})" 
//                             + "|" 
                                   + "(.*\\n*)" 
                             + ")")
                              
                        .build());
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final boolean ordered = matcher.group(3).length() > 1;
        final String items = matcher.group();
        final List list = new List(ordered);
        list.setParent(parent);
        processItems(list, items, parser);
        parent.addChild(list);
    }

    @Override
    protected boolean checkParent(Node parent) {
        return !(parent instanceof Paragraph);
    }

    private void processItems(List parent, String items, Parser parser) {

        final Pattern p = Pattern.compile("^( *)" + BULLET + " (.*\\n*)((?!\\1" + BULLET + " )(.*\\n*))*", Pattern.MULTILINE);
        final Matcher matcher = p.matcher(items);

        final Pattern remove_bullets = Pattern.compile("^ *([*+-]|\\d+\\.) +");
        final Pattern spaces = Pattern.compile("^ {1,4}", Pattern.MULTILINE);


        boolean loose = Pattern.compile("\\n\\n(?!\\s*$)").matcher(items).find();

        while (matcher.find()) {

            String item = matcher.group();
            item = remove_bullets.matcher(item).replaceAll("");
            item = spaces.matcher(item).replaceAll("").trim();

            final ListItem list_item = new ListItem();
            
            if (loose) {
                parser.parse(list_item, item);
                list_item.setParent(parent);
            } else {
                list_item.setParent(parent);
                parser.parse(list_item, item);
            }
            parent.addChild(list_item);
        }

    }
}
