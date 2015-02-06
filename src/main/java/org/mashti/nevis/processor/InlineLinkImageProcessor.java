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

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.mashti.nevis.Parser;
import org.mashti.nevis.element.Image;
import org.mashti.nevis.element.Link;
import org.mashti.nevis.element.Node;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class InlineLinkImageProcessor extends Processor {

    public InlineLinkImageProcessor() {

        super(Pattern.compile("^!?\\[((?:\\[[^\\]]*\\]|[^\\[\\]]|\\](?=[^\\[]*\\]))*)\\]\\(\\s*<?([\\s\\S]*?)>?(?:\\s+['\"]([\\s\\S]*?)['\"])?\\s*\\)"));
    }

    @Override
    public Optional<Node> process(final Matcher matcher, Parser parser) {

        final boolean isImage = matcher.group().startsWith("!");
        final String alt = matcher.group(1);
        final String source = matcher.group(2);
        final String title = matcher.group(3);


        if (isImage) {
            final Image image = new Image(source);
            image.setAlt(alt);
            image.setTitle(title);
            return Optional.of(image);
        } else {
            final Link link = new Link(source);
            link.setTitle(title);
            parser.parseInline(link, alt);
            return Optional.of(link);
        }
    }
}
