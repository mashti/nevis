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
import org.derkani.nevis.element.Node;
import ru.lanwen.verbalregex.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarab Derkani
 */
public abstract class Processor {

    private final Pattern pattern;
//    private final VerbalExpression expression;

    protected Processor(final Pattern pattern) {

        this.pattern = pattern;
    }

    protected Processor(final VerbalExpression expression) {

        this(Pattern.compile(expression.toString()));
        System.out.println(expression);
    }

    public Pattern getPattern() {

        return pattern;
    }

    public abstract void process(Node parent, Matcher matcher, Parser parser);

    protected boolean checkParent(Node parent) {

        return true;
    }

    public String process(Node parent, String value, Parser parser) {

        if (checkParent(parent)) {

            final Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {

                checkMatcher(matcher);

                process(parent, matcher, parser);
                value = value.substring(matcher.end());
            }
        }

        return value;
    }

    private void checkMatcher(Matcher matcher) {

        if (matcher.start() != 0) {
            throw new RuntimeException("processor " + this + matcher.group());
        }
    }
}
