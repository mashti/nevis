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
import org.mashti.nevis.element.Node;
import org.mashti.nevis.element.Paragraph;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class ParagraphProcessor extends Processor {

    public ParagraphProcessor() {

        super(Pattern.compile("(((.+(\\n)?)+)(\\n{2,}|\\z))", Pattern.MULTILINE));
    }

    @Override
    public Optional<Node> process(final Matcher matcher, Parser parser) {

        final String content = matcher.group(2);
        final Paragraph paragraph = new Paragraph();
        if (!content.trim().isEmpty()) {
            parser.parseInline(paragraph, Utils.removeStartAndEndNewLines(content));
        }
        return Optional.of(paragraph);
    }

}
