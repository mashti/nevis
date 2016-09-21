/*
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
import org.derkani.nevis.element.CodeBlock;
import org.derkani.nevis.element.Node;
import org.derkani.nevis.element.Text;
import ru.lanwen.verbalregex.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarab Derkani
 */
public class CodeBlockProcessor extends Processor {

    private static final Pattern TABS = Pattern.compile("^(\\s{4}|\\t)");

    public CodeBlockProcessor() {

//        super(Pattern.compile("^( {4}[^\\n]+\\n*)+"));
        super(VerbalExpression.regex()
                              .searchOneLine(true)
                              .startOfLine()
                              .lineBreak().zeroOrMore()
                              .capture()
                              .then(" ").count(4)
                              .something()
                              .lineBreak().zeroOrMore()
                              .endCapture().oneOrMore()
                              .build());
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {
        
        final String group = matcher.group();
        final String value = Utils.replaceAllPerLine(TABS, group, "");

        final CodeBlock code = new CodeBlock();
        code.setParent(parent);
        code.addChild(new Text(Utils.removeStartAndEndNewLines(value).replaceAll("&", "&amp;")));
        parent.addChild(code);
    }
}
