/*
 * MIT License
 *
 * Copyright (c) 2024-2025. Artem Getmanskii
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.artemget.teleroute.match;

import io.github.artemget.teleroute.update.FkWrap;
import java.util.regex.Pattern;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link MatchRegex}.
 *
 * @since 0.3.0
 */
final class MatchRegexTest {

    @SuppressWarnings("JTCOP.RuleAssertionMessage")
    @Test
    void throwsExceptionWhenRegexNull() {
        Assertions.assertThrows(
            NullPointerException.class,
            () -> new MatchRegex<String>((String) null).test(new FkWrap())
        );
    }

    @SuppressWarnings("JTCOP.RuleAssertionMessage")
    @Test
    void throwsExceptionWhenPatternNull() {
        Assertions.assertThrows(
            NullPointerException.class,
            () -> new MatchRegex<String>((Pattern) null).test(new FkWrap())
        );
    }

    @Test
    void matchesWhenRegex() {
        MatcherAssert.assertThat(
            "Didnt match regex",
            new MatchRegex<String>("\\D+").test(new FkWrap())
        );
    }

    @Test
    void matchesWhenPattern() {
        MatcherAssert.assertThat(
            "Didnt match pattern",
            new MatchRegex<String>(Pattern.compile("\\D+")).test(new FkWrap())
        );
    }
}
