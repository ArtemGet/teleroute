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

package io.github.artemget.teleroute.telegrambots.send;

import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.send.SendException;
import java.io.Serializable;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Send all content laying under {@code BotApiMethod<T>}. Including ancestors of
 * {@code BotApiMethod<Boolean>, BotApiMethod<Message>, BotApiMethod<Serializable>}.
 *
 * @param <T> generic param of BotApiMethod: Boolean, Message, Serializable
 * @see BotApiMethod
 * @since 0.1.0
 */
public final class SendMessageWrap<T extends Serializable> implements Send<AbsSender> {
    /**
     * Message.
     */
    private final BotApiMethod<T> message;

    /**
     * Main ctor.
     *
     * @param message Message
     */
    public SendMessageWrap(final BotApiMethod<T> message) {
        this.message = message;
    }

    @Override
    public void send(final AbsSender send) throws SendException {
        try {
            send.execute(this.message);
        } catch (final TelegramApiException exception) {
            throw new SendException(exception.getMessage(), exception);
        }
    }
}
