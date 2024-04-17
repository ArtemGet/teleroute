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

package com.github.artemget.teleroute.route;

import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.update.UpdWrap;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Route to single command or none.
 *
 * <p><img src="../doc-files/EndRouteScheme.png" width=1000>
 *
 * @param <U> Telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> Sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 * @since 0.0.0
 */
public final class EndRoute<U, S> implements Route<U, S> {
    /**
     * Command.
     */
    private final Collection<Cmd<U, S>> cmd;

    /**
     * Construct EndRoute that actually do nothing.
     */
    public EndRoute() {
        this(Collections.emptyList());
    }

    /**
     * Construct EndRoute that route to command.
     *
     * @param command Command
     */
    public EndRoute(final Cmd<U, S> command) {
        this(Collections.singletonList(command));
    }

    /**
     * Main constructor.
     *
     * @param commands Command
     */
    private EndRoute(final Collection<Cmd<U, S>> commands) {
        this.cmd = commands;
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        return this.cmd.stream().findFirst();
    }
}
