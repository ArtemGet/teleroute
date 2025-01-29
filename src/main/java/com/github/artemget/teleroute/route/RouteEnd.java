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

package com.github.artemget.teleroute.route;

import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.update.Wrap;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Route to single command or none.
 *
 * <p><img src="../doc-files/EndRouteScheme.png" width=1000>
 *
 * @param <U> Update
 * @param <C> Client
 * @since 0.1.0
 */
public final class RouteEnd<U, C> implements Route<U, C> {
    /**
     * Command.
     */
    private final Collection<Cmd<U, C>> cmd;

    /**
     * Ctor.
     */
    public RouteEnd() {
        this(Collections.emptyList());
    }

    /**
     * Ctor.
     *
     * @param command Command
     */
    public RouteEnd(final Cmd<U, C> command) {
        this(Collections.singletonList(command));
    }

    /**
     * Main ctor.
     *
     * @param commands Command
     */
    private RouteEnd(final Collection<Cmd<U, C>> commands) {
        this.cmd = commands;
    }

    @Override
    public Optional<Cmd<U, C>> route(final Wrap<U> update) {
        return this.cmd.stream().findFirst();
    }
}
