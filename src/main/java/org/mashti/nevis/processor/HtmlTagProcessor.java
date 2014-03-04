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
import org.mashti.nevis.element.Html;
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Processor;

/** @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk) */
public class HtmlTagProcessor extends Processor {

    public HtmlTagProcessor() {

        super(Pattern.compile("(" +
                "^<(" + "p|div|h1|h2|h3|h4|h5|h6|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del|span}" + ")" +
                "\\b" +
                "(.+\\n?)*" +
                "</\\2>" +
                "(\\n+|\\z))|" +

                "(?:" +
                "(?<=\\n\\n)" +
                "|" +
                "\\A\\n?" +
                ")" +
                "(" +
                "[ ]{0," + 3 + "}" +
                "<(hr)" +
                "\\b" +
                "([^<>])*?" +
                "/?>" +
                "[ ]*" +
                "(?=\\n{2,}|\\Z))" +
                "|" +
                "(?:" +
                "(?<=\\n\\n)" +
                "|" +
                "\\A\\n?" +
                ")" +
                "(" +
                "[ ]{0," + 3 + "}" +
                "(?s:" +
                "<!" +
                "(--.*?--\\s*)+" +
                ">" +
                ")" +
                "[ ]*" +
                "(?=\\n{2,}|\\Z)|" + "(" +
                "^" +
                "<(" + "p|div|h1|h2|h3|h4|h5|h6|blockquote|pre|table|dl|ol|ul|script|noscript|form|fieldset|iframe|math|ins|del" + ")" +
                "\\b" +
                "(.*\\n)*?" +
                ".*</\\2>" +
                "[ ]*" +
                "(?=\\n+|\\Z))" +
                ")", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void process(Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final Html html = new Html(parent, Utils.removeStartAndEndNewLines(content));

        parent.addChild(html);
    }
}
