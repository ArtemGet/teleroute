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

package com.github.artemget.teleroute.match;

import com.github.artemget.teleroute.update.FkWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link MatchAny}.
 *
 * @since 0.1.0
 */
final class MatchAnyTest {

    @Test
    void matchShouldMatchWhenNoConditionSpecified() {
        Assertions.assertTrue(
            new MatchAny<String>().test(new FkWrap())
        );
    }

    @Test
    void matchShouldMatchWhenAllMatch() {
        Assertions.assertTrue(
            new MatchAny<>(
                new FkMatch(),
                new FkMatch()
            )
                .test(new FkWrap())
        );
    }

    @Test
    void shouldMatchWhenAnyMatch() {
        Assertions.assertTrue(
            new MatchAny<>(
                new FkMatch(false),
                new FkMatch()
            )
                .test(new FkWrap())
        );
    }

    @Test
    void shouldNotMatchWhenNoneMatch() {
        Assertions.assertFalse(
            new MatchAny<>(
                new FkMatch(false),
                new FkMatch(false)
            )
                .test(new FkWrap())
        );
    }
}
