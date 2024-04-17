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
 * Routes to route or command in case of update matching condition,
 * if not - route to other route or command or none.
 *
 * <p><img src="../doc-files/MatchRouteScheme.png" width=1000>
 *
 * @param <U> Telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> Sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 * @since 0.0.0
 */
public final class ForkRoute<U, S> implements Route<U, S> {
    /**
     * Origin route.
     */
    private final Route<U, S> origin;

    /**
     * Spare route.
     */
    private final Route<U, S> spare;

    /**
     * Match condition.
     */
    private final Match<U> match;

    /**
     * Construct FkRoute that routes to match command or none if update not match condition.
     *
     * @param match Match condition
     * @param origin Match command
     */
    public ForkRoute(final Match<U> match, final Cmd<U, S> origin) {
        this(match, new EndRoute<>(origin), new EndRoute<>());
    }

    /**
     * Construct FkRoute that route to match command or spare command if update not match condition.
     *
     * @param match Match condition
     * @param origin Match command
     * @param spare Spare command
     */
    public ForkRoute(final Match<U> match, final Cmd<U, S> origin, final Cmd<U, S> spare) {
        this(match, new EndRoute<>(origin), new EndRoute<>(spare));
    }

    /**
     * Construct FkRoute that route to match route or none if update not match condition.
     *
     * @param match Match condition
     * @param origin Match route
     */
    public ForkRoute(final Match<U> match, final Route<U, S> origin) {
        this(match, origin, new EndRoute<>());
    }

    /**
     * Main constructor.
     * Construct FkRoute that route to match route or spare route if update not match condition.
     *
     * @param match Match condition
     * @param origin Match route
     * @param spare Spare route
     */
    public ForkRoute(final Match<U> match, final Route<U, S> origin, final Route<U, S> spare) {
        this.match = match;
        this.origin = origin;
        this.spare = spare;
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        final Optional<Cmd<U, S>> resp;
        if (this.match.match(update)) {
            resp = this.origin.route(update);
        } else {
            resp = this.spare.route(update);
        }
        return resp;
    }
}
