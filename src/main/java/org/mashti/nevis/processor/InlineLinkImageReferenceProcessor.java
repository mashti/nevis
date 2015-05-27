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
import org.mashti.nevis.element.Image;
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class InlineLinkImageReferenceProcessor extends Processor {

    public InlineLinkImageReferenceProcessor() {

        super(Pattern.compile("^!?\\[((?:\\[[^\\]]*\\]|[^\\[\\]\\\\])*)\\]"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        String id = matcher.group(1);
        if (id == null) {
            id = matcher.group(2);
        }

        final String match = matcher.group();

        if (match.startsWith("!")) {

            final Image image = new Image(null);
            image.setParent(parent);
            image.setMatch(match);
            id = id.replaceAll(" *\\n", " ");
            image.setId(id);

            parser.parse(image, id);
            parent.addChild(image);
        } else {
            final Link link = new Link(null);
            link.setParent(parent);
            link.setMatch(match);
            parser.parse(link, id);
            link.setId(id.replaceAll(" *\\n", " "));
            parent.addChild(link);
        }
    }

    @Override
    protected boolean checkParent(Node parent) {
        return parent.getParent() != null;
    }
}
