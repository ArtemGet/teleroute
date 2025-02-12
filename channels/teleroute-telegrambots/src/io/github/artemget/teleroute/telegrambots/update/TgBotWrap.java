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

package io.github.artemget.teleroute.telegrambots.update;

import io.github.artemget.teleroute.update.Wrap;
import java.util.Optional;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Telegram Update wrap.
 *
 * @since 0.1.0
 */
public final class TgBotWrap implements Wrap<Update> {
    /**
     * Telegram Update.
     */
    private final Update update;

    public TgBotWrap(final Update update) {
        this.update = update;
    }

    @Override
    public Integer identity() {
        return this.update.getUpdateId();
    }

    @Override
    public Boolean isCommand() {
        return Optional.ofNullable(this.update.getMessage())
            .map(Message::isCommand)
            .orElse(false);
    }

    @Override
    public Optional<String> text() {
        return Optional.ofNullable(this.update.getMessage())
            .map(Message::getText);
    }

    @Override
    public Update src() {
        return this.update;
    }
}
