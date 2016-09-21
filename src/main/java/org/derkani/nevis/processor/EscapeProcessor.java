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
import org.derkani.nevis.element.Escaped;
import org.derkani.nevis.element.Node;
import ru.lanwen.verbalregex.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarab Derkani
 */
public class EscapeProcessor extends Processor {

    public EscapeProcessor() {

//        super(Pattern.compile("^\\\\([\\\\`*{}\\[\\]\\(\\)#+\\-.!_>])"));

        super(VerbalExpression.regex()
                              .searchOneLine(true)
                              .startOfLine()
                              .then("\\")
                              .capture()
                              .anyOf("[]{}()`*\\#+-_.!<>")
                              .endCapture()
                              .build());
    }

    @Override
    public void process(final Node parent, final Matcher matcher, Parser parser) {

        final String value = matcher.group(1);
        final Escaped escaped = new Escaped();
        escaped.setParent(parent);
        parser.parse(escaped, value);
        parent.addChild(escaped);
    }

    @Override
    protected boolean checkParent(Node parent) {
        return parent.getParent() != null;
    }
}
