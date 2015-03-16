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

import org.mashti.nevis.Parser;
import org.mashti.nevis.element.BlockQuote;
import org.mashti.nevis.element.ListItem;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Paragraph;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class ParagraphProcessor extends Processor {

    public ParagraphProcessor() {

        super(Pattern.compile("^((?:[^\\n]+\\n?(?!( *[-*_]){3,} *(?:\\n+|$)| *(#{1,6}) *([^\\n]+?) *#* *(?:\\n+|$)|([^\\n]+)\\n *(=|-){2,} *(?:\\n+|$)|( *>[^\\n]+(\\n(?! *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$))[^\\n]+)*\\n*)+|<(?!(?:a|em|strong|small|s|cite|q|dfn|abbr|data|time|code|var|samp|kbd|sub|sup|i|b|u|mark|ruby|rt|rp|bdi|bdo|span|br|wbr|ins|del|img)\\b)\\w+(?!:/|[^\\w\\s@]*@)\\b| *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$)))+)\\n*"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final Paragraph paragraph = new Paragraph();
        paragraph.setPatent(parent);
        if (!content.trim().isEmpty()) {
            parser.parse(paragraph, Utils.removeStartAndEndNewLines(content));
        }

        parent.addChild(paragraph);
    }

    @Override
    protected boolean matchesParent(Node parent) {
        return parent.getPatent() == null || parent instanceof ListItem || parent instanceof BlockQuote;
    }
}
