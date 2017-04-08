package org.derkani.nevis;

import org.junit.*;

import java.util.regex.*;

import static org.junit.Assert.*;

/**
 * @author masih
 */
public class HumanReadablePatternTest {

    private HumanReadablePattern.Builder builder;

    @Before
    public void setUp() throws Exception {

        builder = HumanReadablePattern.builder();
    }

    @Test
    public void startThenWorks() throws Exception {

        final HumanReadablePattern fish = builder.beginningOfLine()
                        .then("fish").then("123").endOfLine().build();

        final Pattern pattern = fish.getPattern();
        assertTrue(pattern.matcher("fish123").find());
    }
}
