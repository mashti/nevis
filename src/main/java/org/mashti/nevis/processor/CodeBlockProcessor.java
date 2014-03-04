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
import org.mashti.nevis.element.CodeBlock;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;
import org.mashti.nevis.element.Text;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class CodeBlockProcessor extends Processor {

    private static final Pattern TABS = Pattern.compile("^(\\s{4}|\\t)");

    public CodeBlockProcessor() {

        super(Pattern.compile("" +
                "(?:\\n\\n|\\A)" +
                "((?:" +
                "(?:[ ]{4})" +
                ".*\\n+" +
                ")+" +
                ")" +
                "((?=^[ ]{0,4}\\S)|\\Z)", Pattern.MULTILINE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        String value = Utils.replaceAllPerLine(TABS, matcher.group(1), "");

        final CodeBlock code = new CodeBlock(parent);
        code.addChild(new Text(code, Utils.removeStartAndEndNewLines(value)));
        parent.addChild(code);
    }
}
