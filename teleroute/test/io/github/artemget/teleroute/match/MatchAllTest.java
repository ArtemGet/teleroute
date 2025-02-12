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
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link MatchAll}.
 *
 * @since 0.1.0
 */
final class MatchAllTest {

    @Test
    void matchesWhenNoConditionSpecified() {
        MatcherAssert.assertThat(
            "Didnt match without condition",
            new MatchAll<String>().test(new FkWrap())
        );
    }

    @Test
    void matchesWhenAllMatch() {
        MatcherAssert.assertThat(
            "Didnt match valid matches",
            new MatchAll<>(
                new FkMatch(),
                new FkMatch()
            ).test(new FkWrap())
        );
    }

    @Test
    void matchesNotWhenOneNotMatch() {
        MatcherAssert.assertThat(
            "Matched all when one match is invalid",
            !new MatchAll<>(
                new FkMatch(false),
                new FkMatch()
            ).test(new FkWrap())
        );
    }
}
