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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class HtmlTagProcessor extends Processor {


    private static final String TAG = "(?:"
            + "a|em|strong|small|s|cite|q|dfn|abbr|data|time|code"
            + "|var|samp|kbd|sub|sup|i|b|u|mark|ruby|rt|rp|bdi|bdo"
            + "|span|br|wbr|ins|del|img|hr|div|h[1-6]|p|blockquote"
            + "|pre|table|dl|ol|ul|script|noscript|form|fieldset"
            + "|iframe|math|ins)";

    private static final String COMMENT = "<!--[\\s\\S]*?-->";
    private static final String CLOSED = "<(" + TAG +
            ").*?</\\1>";
    private static final String CLOSING = "<" + TAG +
            "(?:\"[^\"]*\"|'[^']*'|[^'\">])*?>";
    public static final String PATTERN_1 = "^ *(?:" +
            COMMENT +
            "|" +
            CLOSED +
            "|" +
            CLOSING +
            ") *(?:\\n{2,}|\\s*$)";

    public HtmlTagProcessor() {

        super(Pattern.compile(PATTERN_1, Pattern.DOTALL | Pattern.CASE_INSENSITIVE));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final Html child = new Html(Utils.removeStartAndEndNewLines(content));
        child.setParent(parent);
        parent.addChild(child);

    }

    @Override
    protected boolean checkParent(Node parent) {
        return parent.getParent() == null;
    }
}
