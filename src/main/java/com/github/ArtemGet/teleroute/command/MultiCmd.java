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

package com.github.ArtemGet.teleroute.command;

import com.github.ArtemGet.teleroute.send.MultiSend;
import com.github.ArtemGet.teleroute.send.Send;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Execute many commands as one.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class MultiCmd<U, S> implements Cmd<U, S> {
    private static final Logger log = LoggerFactory.getLogger(MultiCmd.class);
    private final Collection<Cmd<U, S>> commands;

    /**
     * Construct MultiCmd that execute one or many commands.
     *
     * @param commands commands to execute
     */
    @SafeVarargs
    public MultiCmd(final Cmd<U, S>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Main constructor. Construct MultiCmd that execute collection of commands.
     *
     * @param commands commands to execute
     */
    public MultiCmd(final Collection<Cmd<U, S>> commands) {
        this.commands = Collections.unmodifiableCollection(commands);
    }

    @Override
    public Optional<Send<S>> execute(final U update) {
        return this.form(this.executeCmds(update));
    }

    private Optional<Send<S>> form(List<Send<S>> sends) {
        if (sends.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new MultiSend<>(sends));
    }

    private List<Send<S>> executeCmds(final U update) {
        return this.commands.stream()
                .map(cmd -> {
                    try {
                        return cmd.execute(update);
                    } catch (Exception e) {
                        log.warn("Error execute cmd: {}", e.getMessage(), e);
                        return Optional.<Send<S>>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
