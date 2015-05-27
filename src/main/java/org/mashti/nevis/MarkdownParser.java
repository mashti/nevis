/**
 * This file is part of nevis.
 * <p>
 * nevis is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * nevis is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with nevis.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.mashti.nevis;

import org.mashti.nevis.element.Node;
import org.mashti.nevis.processor.*;

import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public class MarkdownParser extends Parser {

    static final Pattern NEW_LINE = Pattern.compile("(\\n\\r|\\r|\u2424)");

    static final Processor[] PROCESSORS = {
            new NewLineProcessor(),
            new CodeBlockProcessor(),
            new HeadingProcessor(),
            new HeadingSetextProcessor(),
            new HorizontalRuleProcessor(),
            new BlockQuoteProcessor(),
            new ListProcessor(),
            new HtmlTagProcessor(),
            new InlineHtmlTagProcessor(),
            new DefinitionProcessor(),
            new EscapeProcessor(),
            new ListProcessor(),
            new InlineCodeProcessor(),
            new AutoLinkProcessor(),
            new InlineLinkImageProcessor(),
            new InlineLinkProcessor(),
            new InlineLinkImageReferenceProcessor(),
            new StrongProcessor(),
            new EmphasizedProcessor(),
            new BreakLineProcessor(),
            new TextProcessor(),
            new ParagraphProcessor(),
    };

    private static final Pattern UNICODE_WHITESPACE = Pattern.compile("\u00a0", Pattern.MULTILINE);
    private static final Pattern TAB = Pattern.compile("\\t");
    public static final Pattern SPACE_LINE = Pattern.compile("^ *$", Pattern.MULTILINE);

    public MarkdownParser() {
        super(PROCESSORS);
    }

    @Override
    public Node parse(String value) {

        final String sanitised_value = sanitise(value);
        return super.parse(sanitised_value);
    }

    private String sanitise(String value) {

        value = NEW_LINE.matcher(value).replaceAll("\n");
        value = UNICODE_WHITESPACE.matcher(value).replaceAll(" ");
        value = TAB.matcher(value).replaceAll("    ");
        value = SPACE_LINE.matcher(value).replaceAll("");

        return value;
    }
}
