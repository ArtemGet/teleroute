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

package com.github.ArtemGet.teleroute.update;

import java.util.Optional;

public class FkUpdWrap implements UpdWrap<String> {
    private static final Integer ID = 123;
    private static final Boolean IS_COMMAND = true;
    private static final String TEXT = "text";
    private static final String SRC = "src";

    private final Integer id;
    private final Boolean isCommand;
    private final String text;
    private final String src;

    public FkUpdWrap() {
        this(
                FkUpdWrap.ID,
                FkUpdWrap.IS_COMMAND,
                FkUpdWrap.TEXT,
                FkUpdWrap.SRC
        );
    }

    public FkUpdWrap(
            Integer id,
            Boolean isCommand,
            String text,
            String src
    ) {
        this.id = id;
        this.isCommand = isCommand;
        this.text = text;
        this.src = src;
    }

    @Override
    public Integer id() {
        return this.id;
    }

    @Override
    public Boolean isCommand() {
        return this.isCommand;
    }

    @Override
    public Optional<String> text() {
        return Optional.of(this.text);
    }

    @Override
    public String src() {
        return this.src;
    }
}
