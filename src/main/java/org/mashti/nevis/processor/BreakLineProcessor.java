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
import org.mashti.nevis.element.BreakLine;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BreakLineProcessor extends Processor {

    public BreakLineProcessor() {

        super(Pattern.compile(" {2,}\\n", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final BreakLine bold = new BreakLine(parent);
        parent.addChild(bold);
    }
}
