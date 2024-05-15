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
import com.github.artemget.teleroute.update.Wrap;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Fork route. Pick origin or spare route.
 *
 * <p><img src="../doc-files/MatchRouteScheme.png" width=1000>
 *
 * @param <U> Update
 * @param <C> Client
 * @since 0.1.0
 */
public final class RouteFork<U, C> implements Route<U, C> {
    /**
     * Origin route.
     */
    private final Route<U, C> origin;

    /**
     * Spare route.
     */
    private final Route<U, C> spare;

    /**
     * Match condition.
     */
    private final Predicate<Wrap<U>> match;

    /**
     * Ctor.
     *
     * @param match Condition
     * @param origin Command
     */
    public RouteFork(final Predicate<Wrap<U>> match, final Cmd<U, C> origin) {
        this(match, new RouteEnd<>(origin), new RouteEnd<>());
    }

    /**
     * Ctor.
     *
     * @param match Condition
     * @param origin Command
     * @param spare Command
     */
    public RouteFork(
        final Predicate<Wrap<U>> match,
        final Cmd<U, C> origin,
        final Cmd<U, C> spare
    ) {
        this(match, new RouteEnd<>(origin), new RouteEnd<>(spare));
    }

    /**
     * Ctor.
     *
     * @param match Condition
     * @param origin Route
     */
    public RouteFork(final Predicate<Wrap<U>> match, final Route<U, C> origin) {
        this(match, origin, new RouteEnd<>());
    }

    /**
     * Main ctor.
     *
     * @param match Condition
     * @param origin Route
     * @param spare Route
     */
    public RouteFork(
        final Predicate<Wrap<U>> match,
        final Route<U, C> origin,
        final Route<U, C> spare
    ) {
        this.match = match;
        this.origin = origin;
        this.spare = spare;
    }

    @Override
    public Optional<Cmd<U, C>> route(final Wrap<U> update) {
        final Optional<Cmd<U, C>> resp;
        if (this.match.test(update)) {
            resp = this.origin.route(update);
        } else {
            resp = this.spare.route(update);
        }
        return resp;
    }
}
