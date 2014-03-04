package org.mashti.nevis.processor;

import java.util.Scanner;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringEscapeUtils;

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

        final Scanner scanner = new Scanner(value);
        final StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            result.append(pattern.matcher(line).replaceAll(replacement));
            result.append("\n");
        }
        return result.toString();
    }

    public static String escapeHtml(String value) {

        return StringEscapeUtils.escapeHtml(StringEscapeUtils.unescapeHtml(value));
    }

    static boolean isEmail(String destination) {

        return destination.contains("@");
    }
}
