/**
 * Copyright © 2014, Masih H. Derkani
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.derkani.nevis.processor;

import org.derkani.nevis.Parser;
import org.derkani.nevis.element.Html;
import org.derkani.nevis.element.Node;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarab Derkani
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
    public static final String PATTERN_1 = "^\\n* *(?:" +
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
