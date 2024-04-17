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

package com.github.ArtemGet.teleroute.match.common;

import com.github.ArtemGet.teleroute.match.Match;
import com.github.ArtemGet.teleroute.update.UpdWrap;

/**
 * Check provided text occur in update's message's text
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class OccurTxtMatch<U> implements Match<U> {
    private final String text;

    /**
     * Main constructor. Construct OccurTxtMatch.
     *
     * @param text text to match
     */
    public OccurTxtMatch(final String text) {
        this.text = text;
    }

    @Override
    public Boolean match(final UpdWrap<U> update) {
        return update.text()
                .map(text -> text.contains(this.text))
                .orElse(false);
    }
}
