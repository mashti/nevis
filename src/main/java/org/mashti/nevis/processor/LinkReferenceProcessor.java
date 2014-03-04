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

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class LinkReferenceProcessor extends Processor {

    public LinkReferenceProcessor() {

        super(Pattern.compile("^[ ]{0,3}\\[(.+)\\]:" + // ID = $1
                "[ \\t]*\\n?[ \\t]*" + // Space
                "<?(\\S+?)>?" + // URL = $2
                "[ \\t]*\\n?[ \\t]*" + // Space
                "(?:[\"(](.+?)[\")][ \\t]*)?" + // Optional title = $3
                "(?:\\n+|\\Z)", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String id = matcher.group(1).toLowerCase();
        final String destination = matcher.group(2);
        final String title = matcher.group(3);
        final Link link = new Link(parent, Utils.isEmail(destination) ? "mailto:" + destination : destination);
        link.setTitle(title);
        parser.referenceLink(id, link);
    }
}
