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

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class InlineLinkProcessor extends Processor {

    public InlineLinkProcessor() {

        super(Pattern.compile("(" +
                "\\[(.*?)\\]" + // Link text = $2
                "\\(" +
                "[ \\t]*" +
                "<?(.*?)>?" + // href = $3
                "[ \\t]*" +
                "(" +
                "(['\"])" + // Quote character = $5
                "(.*?)" + // Title = $6
                "\\5" +
                "[ \\t]*)?" +
                "\\)" +
                ")", Pattern.DOTALL));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String text = matcher.group(2);
        final String destination = matcher.group(3);
        final String title = matcher.group(6);

        final Link link = new Link(parent, destination);
        link.setTitle(title);
        link.addChild(new Text(link, text));

        parent.addChild(link);
    }
}
