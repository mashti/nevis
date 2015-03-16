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
import org.mashti.nevis.element.Node;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public abstract class Processor {

    private final Pattern pattern;

    protected Processor(final Pattern pattern) {

        this.pattern = pattern;
    }

    public Pattern pattern() {
        return pattern;
    }

    public abstract void process(Node parent, Matcher matcher, Parser parser);

    protected boolean matchesParent(Node parent) {
        return true;
    }

    public String process(Node parent, String value, Parser parser) {

        if (!matchesParent(parent)) {
            return value;
        }

        final Matcher matcher = pattern.matcher(value);

        if (matcher.find()) {

            if (matcher.start() != 0) {
                throw new RuntimeException("processor " + this);
            }

            process(parent, matcher, parser);
            return value.substring(matcher.end());
        } else {
            return value;
        }
    }
}