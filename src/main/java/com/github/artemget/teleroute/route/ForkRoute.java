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
import com.github.artemget.teleroute.match.Match;
import com.github.artemget.teleroute.update.UpdWrap;

import java.util.Optional;

/**
 * Fork route.
 * Routes to route or command in case of update matching condition, if not - route to other route or command or none.
 *
 * <p><img src="../doc-files/MatchRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class ForkRoute<U, S> implements Route<U, S> {
    private final Route<U, S> origin;
    private final Route<U, S> spare;
    private final Match<U> match;

    /**
     * Construct FkRoute that routes to match command or none if update not match condition.
     *
     * @param match   match condition
     * @param origin match command
     */
    public ForkRoute(final Match<U> match, final Cmd<U, S> origin) {
        this(match, new EndRoute<>(origin), new EndRoute<>());
    }

    /**
     * Construct FkRoute that route to match command or spare command if update not match condition.
     *
     * @param match      match condition
     * @param origin    match command
     * @param spare spare command
     */
    public ForkRoute(final Match<U> match, final Cmd<U, S> origin, final Cmd<U, S> spare) {
        this(match, new EndRoute<>(origin), new EndRoute<>(spare));
    }

    /**
     * Construct FkRoute that route to match route or none if update not match condition.
     *
     * @param match match condition
     * @param origin match route
     */
    public ForkRoute(final Match<U> match, final Route<U, S> origin) {
        this(match, origin, new EndRoute<>());
    }

    /**
     * Main constructor. Construct FkRoute that route to match route or spare route if update not match condition.
     *
     * @param match      match condition
     * @param origin      match route
     * @param spare spare route
     */
    public ForkRoute(final Match<U> match, final Route<U, S> origin, final Route<U, S> spare) {
        this.match = match;
        this.origin = origin;
        this.spare = spare;
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        if (!match.match(update)) {
            return spare.route(update);
        }
        return this.origin.route(update);
    }
}
