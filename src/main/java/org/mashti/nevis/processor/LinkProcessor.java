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
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class LinkProcessor extends Processor {

    private static final Pattern NEW_LINE_IN_TEXT = Pattern.compile(" *\\n");

    public LinkProcessor() {

        super(Pattern.compile("^(?<!\\\\)\\[(.*?)(?<!\\\\)\\](?: *\\n*(?<!\\\\)\\[(.*?)(?<!\\\\)\\])?", Pattern.DOTALL));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        String id = matcher.group(2);
        final String match = matcher.group();
        final String text = matcher.group(1);

        final Link link = new Link(null);
        link.setPatent(parent);
        if (id == null || id.trim().isEmpty()) {
            id = NEW_LINE_IN_TEXT.matcher(text).replaceAll(" ");
        }
        link.setMatch(match);
        link.setId(id);
        parser.parse(link, text);
        parent.addChild(link);
    }

    @Override
    protected boolean matchesParent(Node parent) {
        return parent.getPatent() != null;
    }
}
