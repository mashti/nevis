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
public class HeadingSetextProcessor extends Processor {

    public HeadingSetextProcessor() {

        super(Pattern.compile("^([^\\n]+)\\n *(=|-){2,} *(?:\\n+|$)"));
    }

    @Override
    public Optional<Node> process(final Matcher matcher, Parser parser) {

        final String level_group = matcher.group(2);
        final int level = level_group.startsWith("-") ? 2 : 1;
        final String content = matcher.group(1);
        final Heading heading = new Heading(level);

        heading.addChild(new Text(content));
        return Optional.of(heading);
    }

}
