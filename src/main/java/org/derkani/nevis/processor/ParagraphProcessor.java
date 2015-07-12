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
import org.derkani.nevis.element.BlockQuote;
import org.derkani.nevis.element.ListItem;
import org.derkani.nevis.element.Node;
import org.derkani.nevis.element.Paragraph;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarab Derkani
 */
public class ParagraphProcessor extends Processor {

    public ParagraphProcessor() {

        super(Pattern.compile("^((?:[^\\n]+\\n?(?!( *[-*_]){3,} *(?:\\n+|$)| *(#{1,6}) *([^\\n]+?) *#* *(?:\\n+|$)|([^\\n]+)\\n *(=|-){2,} *(?:\\n+|$)|( *>[^\\n]+(\\n(?! *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$))[^\\n]+)*\\n*)+|<(?!(?:a|em|strong|small|s|cite|q|dfn|abbr|data|time|code|var|samp|kbd|sub|sup|i|b|u|mark|ruby|rt|rp|bdi|bdo|span|br|wbr|ins|del|img)\\b)\\w+(?!:/|[^\\w\\s@]*@)\\b| *\\[([^\\]]+)\\]: *<?([^\\s>]+)>?(?: +[\"(]([^\\n]+)[\")])? *(?:\\n+|$)))+)\\n*"));
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final String content = matcher.group();
        final Paragraph paragraph = new Paragraph();
        paragraph.setParent(parent);
        if (!content.trim().isEmpty()) {
            parser.parse(paragraph, Utils.removeStartAndEndNewLines(content));
        }

        parent.addChild(paragraph);
    }

    @Override
    protected boolean checkParent(Node parent) {
        return parent.getParent() == null || parent instanceof ListItem || parent instanceof BlockQuote;
    }
}
