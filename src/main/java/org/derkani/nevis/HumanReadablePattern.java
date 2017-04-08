package org.derkani.nevis;

import java.util.*;
import java.util.regex.*;

/**
 * @author masih
 */
public class HumanReadablePattern {

    private final Pattern pattern;

    public Pattern getPattern() {

        return pattern;
    }

    public static class Builder {

        private static final char BEGINNING = '^';
        private static final char END = '$';
        private final StringBuilder regex = new StringBuilder();

        private final Set<String> namedGroups = new HashSet<>();

        public Builder beginningOfLine() {

            if (!Objects.equals(getFirstChar(), BEGINNING)) {
                regex.insert(0, BEGINNING);
            }
            return this;
        }

        public Builder endOfLine() {

            if (!Objects.equals(getFirstChar(), END)) {
                regex.append(END);
            }
            return this;
        }

        public Builder capture(String name, Builder builder) {

            requireSomeAlphanumericsStartingWithLetter(name);
            if (namedGroups.contains(name) || builder.namedGroups.contains(name)) {
                throw new IllegalArgumentException("capturing group already exists with name: " + name);
            }

            regex.append("(?<").append(name).append(">").append(builder.build().pattern.toString());

            return this;
        }

        public Builder then(String value) {

            final String quotedValue = Pattern.quote(value);
            regex.append("(?:").append(quotedValue).append(')');
            return this;
        }

        public Builder maybe(String value){
            
            then(value);
            regex.append('?');
            
            return this;
        }
        
        private void requireSomeAlphanumericsStartingWithLetter(final String value) {

            if (!value.matches("[a-zA-Z]+[a-zA-Z0-9]*")) {
                throw new IllegalArgumentException("must only contain letters and/or digits: " + value);
            }
        }

        private Character getFirstChar() {

            if (regex.length() != 0) {
                final char[] first = new char[1];
                regex.getChars(0, 1, first, 0);
                return first[0];
            }
            return null;
        }

        private Character getLastChar() {

            if (regex.length() != 0) {
                final char[] last = new char[1];
                final int length = regex.length();
                regex.getChars(length - 2, length - 1, last, 0);
                return last[0];
            }
            return null;
        }

        public HumanReadablePattern build() {

            return new HumanReadablePattern(Pattern.compile(regex.toString(), Pattern.MULTILINE));
        }
    }

    private HumanReadablePattern(Pattern pattern) {

        this.pattern = pattern;
    }

    public static Builder builder() {

        return new Builder();
    }

}
