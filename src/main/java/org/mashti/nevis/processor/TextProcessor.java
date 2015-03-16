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
import org.mashti.nevis.element.BlockQuote;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Text;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class TextProcessor extends Processor {

    public TextProcessor() {

//        super(Pattern.compile("^[^\n]+"));
        super(Pattern.compile("^[\\s\\S]+?(?=[\\\\<!\\[_*`]| {2,}\\n|$)"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group();
        final Text text = new Text(value);
        text.setPatent(parent);
        parent.addChild(text);
    }

    @Override
    protected boolean matchesParent(Node parent) {
        return parent.getPatent() != null && !(parent instanceof BlockQuote);
    }
}
