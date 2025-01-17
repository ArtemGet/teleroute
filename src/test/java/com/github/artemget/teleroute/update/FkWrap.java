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

package com.github.artemget.teleroute.update;

import java.util.Optional;

/**
 * Fake Update.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"JTCOP.RuleAllTestsHaveProductionClass", "JTCOP.RuleCorrectTestName"})
public final class FkWrap implements Wrap<String> {
    /**
     * Default identity.
     */
    private static final Integer ID = 123;

    /**
     * Telegram command.
     */
    private static final Boolean COMMAND = true;

    /**
     * Content.
     */
    private static final String DEFAULT_TEXT = "text";

    /**
     * Id.
     */
    private final Integer id;

    /**
     * Either command or not.
     */
    private final Boolean command;

    /**
     * Update content.
     */
    private final String content;

    public FkWrap() {
        this(
            FkWrap.ID,
            FkWrap.COMMAND,
            FkWrap.DEFAULT_TEXT
        );
    }

    public FkWrap(
        final Integer id,
        final Boolean command,
        final String content
    ) {
        this.id = id;
        this.command = command;
        this.content = content;
    }

    @Override
    public Integer identity() {
        return this.id;
    }

    @Override
    public Boolean isCommand() {
        return this.command;
    }

    @Override
    public Optional<String> text() {
        return Optional.of(this.src());
    }

    @Override
    public String src() {
        return this.content;
    }
}
