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

package com.github.artemget.teleroute.command;

import com.github.artemget.teleroute.send.MultiSend;
import com.github.artemget.teleroute.send.Send;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Execute many commands as one.
 *
 * @param <U> Telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> Sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 * @since 0.0.0
 */
public final class MultiCmd<U, S> implements Cmd<U, S> {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(MultiCmd.class);

    /**
     * Commands.
     */
    private final Collection<Cmd<U, S>> commands;

    /**
     * Construct MultiCmd that execute one or many commands.
     *
     * @param commands Commands to execute
     */
    @SafeVarargs
    public MultiCmd(final Cmd<U, S>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Main constructor. Construct MultiCmd that execute collection of commands.
     *
     * @param commands Commands to execute
     */
    public MultiCmd(final Collection<Cmd<U, S>> commands) {
        this.commands = Collections.unmodifiableCollection(commands);
    }

    @Override
    public Optional<Send<S>> execute(final U update) {
        final List<Send<S>> sends = this.executeCmds(update);
        final Optional<Send<S>> resp;
        if (sends.isEmpty()) {
            resp = Optional.empty();
        } else {
            resp = Optional.of(new MultiSend<>(sends));
        }
        return resp;
    }

    private List<Send<S>> executeCmds(final U update) {
        return this.commands.stream()
            .map(
                cmd -> {
                    Optional<Send<S>> resp;
                    try {
                        resp = cmd.execute(update);
                    } catch (final CmdException exception) {
                        MultiCmd.LOG.warn(
                            "Error execute cmd: {}",
                            exception.getMessage(),
                            exception
                        );
                        resp = Optional.empty();
                    }
                    return resp;
                })
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }
}
