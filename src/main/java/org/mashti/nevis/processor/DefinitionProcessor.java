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
import org.mashti.nevis.element.LinkDefinition;
import org.mashti.nevis.element.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class DefinitionProcessor extends Processor {


    public DefinitionProcessor() {

        super(Pattern.compile("^ *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$)"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final String id = matcher.group(1).toLowerCase();
        final String destination = matcher.group(2);
        final String title = matcher.group(3);
        final LinkDefinition link = new LinkDefinition(destination);
        link.setParent(parent);
        link.setId(id);
        link.setTitle(title);
        parent.addChild(link);
    }
}
