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
import org.mashti.nevis.element.Bold;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class BoldProcessor extends Processor {

    public BoldProcessor() {

        super(Pattern.compile("(\\*{2}|_{2})(?=\\S)(.+?[*_]*)(?<=\\S)\\1", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group(2);
        final Bold bold = new Bold(parent);
        parser.parseInline(bold, value);
        parent.addChild(bold);
    }
}
