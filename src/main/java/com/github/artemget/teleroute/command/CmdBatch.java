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

import com.github.artemget.teleroute.send.Send;
import com.github.artemget.teleroute.send.SendBatch;
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
 * @param <U> Update
 * @param <C> Client
 * @since 0.1.0
 */
public final class CmdBatch<U, C> implements Cmd<U, C> {
    /**
     * Logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(CmdBatch.class);

    /**
     * Commands.
     */
    private final Collection<Cmd<U, C>> commands;

    /**
     * Ctor.
     *
     * @param commands Commands to execute
     */
    @SafeVarargs
    public CmdBatch(final Cmd<U, C>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Main ctor.
     *
     * @param commands Commands to execute
     */
    public CmdBatch(final Collection<Cmd<U, C>> commands) {
        this.commands = Collections.unmodifiableCollection(commands);
    }

    @Override
    public Optional<Send<C>> execute(final U update) {
        final List<Send<C>> sends = this.executeCmds(update);
        final Optional<Send<C>> resp;
        if (sends.isEmpty()) {
            resp = Optional.empty();
        } else {
            resp = Optional.of(new SendBatch<>(sends));
        }
        return resp;
    }

    private List<Send<C>> executeCmds(final U update) {
        return this.commands.stream()
            .map(
                cmd -> {
                    Optional<Send<C>> resp;
                    try {
                        resp = cmd.execute(update);
                    } catch (final CmdException exception) {
                        CmdBatch.LOG.warn(
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
