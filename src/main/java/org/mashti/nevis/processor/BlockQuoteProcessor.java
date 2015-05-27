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
import org.mashti.nevis.element.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class BlockQuoteProcessor extends Processor {

    public BlockQuoteProcessor() {

        super(Pattern.compile("^( *>[^\\n]+(\\n(?! *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$))[^\\n]+)*\\n*)+"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        String content = matcher.group();
        content = Pattern.compile("^ *> ?", Pattern.MULTILINE).matcher(content).replaceAll("");

        final BlockQuote block_quote = new BlockQuote();
        block_quote.setParent(parent);
        parent.addChild(block_quote);
        if (!content.trim().isEmpty()) {
            parser.parse(block_quote, content);
        }
    }

}
