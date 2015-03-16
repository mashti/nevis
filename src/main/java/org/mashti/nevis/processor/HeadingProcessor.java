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
import org.mashti.nevis.element.Heading;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Text;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class HeadingProcessor extends Processor {

    public HeadingProcessor() {

        super(Pattern.compile("^ *(#{1,6}) *([^\\n]+?) *\\1? *(?:\\n+|$)"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final int level = matcher.group(1).length();
        final String content = matcher.group(2);
        final Heading heading = new Heading(level);
        heading.setPatent(parent);
        heading.addChild(new Text(content));
        parent.addChild(heading);
    }

}
