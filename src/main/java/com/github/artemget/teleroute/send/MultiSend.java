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

package com.github.artemget.teleroute.send;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of batch send, use in case you need to send many messages at the same time.
 *
 * @param <S> Actually sends messages, ie AdsSender from telegrambots or your own impl of tg send
 * @since 0.0.0
 */
public final class MultiSend<S> implements Send<S> {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MultiSend.class);

    /**
     * Sends.
     */
    private final Collection<Send<S>> sends;

    /**
     * Construct MultiSend of one or many Send objects.
     *
     * @param sends One or many Send objects
     */
    @SafeVarargs
    public MultiSend(final Send<S>... sends) {
        this(Arrays.asList(sends));
    }

    /**
     * Main constructor. Construct MultiSend of many Send objects.
     *
     * @param sends Collection of Send objects
     */
    public MultiSend(final Collection<Send<S>> sends) {
        this.sends = Collections.unmodifiableCollection(sends);
    }

    @Override
    public void send(final S client) {
        this.sends.forEach(
            send -> {
                try {
                    send.send(client);
                } catch (final SendException exception) {
                    MultiSend.LOG.warn(
                        "Unable to send message: {}",
                        exception.getMessage(),
                        exception
                    );
                }
            });
    }
}
