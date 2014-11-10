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
import org.mashti.nevis.element.Processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BlockQuoteProcessor extends Processor {

    public BlockQuoteProcessor() {

        super(Pattern.compile("(" +
                "(" +
                "^[ \t]*>[ \t]?" + // > at the start of a line
                ".+\\n" + // rest of the first line
                "(.+\\n)*" + // subsequent consecutive lines
                "\\n*" + // blanks
                ")+" +
                ")", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        String content = matcher.group(1);
        content = Pattern.compile("^[ \t]*>[ \t]?", Pattern.MULTILINE).matcher(content).replaceAll("");
        content = Pattern.compile("^[ \t]+$", Pattern.MULTILINE).matcher(content).replaceAll("");

        final BlockQuote block_quote = new BlockQuote(parent);
        if (!content.trim().isEmpty()) {
            parser.parseBlock(block_quote, content);
        }
        parent.addChild(block_quote);
    }

}
