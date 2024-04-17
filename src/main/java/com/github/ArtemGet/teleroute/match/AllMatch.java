/*
 * MIT License
 *
 * Copyright (c) 2024 Artem Getmanskii
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

package com.github.ArtemGet.teleroute.match;

import com.github.ArtemGet.teleroute.update.UpdWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Check update match all condition.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class AllMatch<U> implements Match<U> {
    private final Collection<Match<U>> matches;

    /**
     * Construct AllMatch contains one or many conditions.
     *
     * @param matches conditions
     */
    @SafeVarargs
    public AllMatch(final Match<U>... matches) {
        this(Arrays.asList(matches));
    }

    /**
     * Main constructor. Construct AllMatch contains collection of conditions.
     *
     * @param matches conditions
     */
    public AllMatch(final Collection<Match<U>> matches) {
        this.matches = Collections.unmodifiableCollection(matches);
    }

    @Override
    public Boolean match(final UpdWrap<U> update) {
        return this.matches.stream()
                .allMatch(match -> match.match(update));
    }
}
