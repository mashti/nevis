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
package org.mashti.nevis.processor;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Masih Hajiarabderkani (mh638@st-andrews.ac.uk)
 */
public final class Utils {

    private Utils() {

    }

    static String removeStartAndEndNewLines(final String content) {

        return content.replaceAll("^\\n*", "").replaceAll("\\n*$", "");
    }

    static String replaceAllPerLine(Pattern pattern, String value, String replacement) {

        final StringBuilder result = new StringBuilder();
        try (Scanner scanner = new Scanner(value)) {
            while (scanner.hasNextLine()) {
                final String line = scanner.nextLine();
                result.append(pattern.matcher(line).replaceAll(replacement));
                result.append("\n");
            }
        }

        return result.toString();
    }

    public static String escapeHtml(String value) {

        return value.replaceAll("&(?![^&]*;)", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");//  StringEscapeUtils.escapeHtml(value);
    }

    public static String escapeHtmlAttribute(String value) {

        return value.replaceAll("\"", "&quot;").replaceAll("&(?![^&]*;)", "&amp;");
    }

    static boolean isEmail(String destination) {

        return destination.contains("@");
    }
}
