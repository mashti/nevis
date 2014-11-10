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
import org.mashti.nevis.element.Html;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HtmlInlineTagProcessor extends Processor {

    public HtmlInlineTagProcessor() {

        super(Pattern.compile("(" +
                "" +
                "<(" + "p|div|h1|h2|h3|h4|h5|h6|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del|span" + ")" +
                "\\b" +
                "(.*\\n)*?" +
                ".*</\\2>" +
                "[ ]*" +
                ")", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final String value = Utils.removeStartAndEndNewLines(content);
        final Html html = new Html(parent, value);
        parent.addChild(html);
    }
}
