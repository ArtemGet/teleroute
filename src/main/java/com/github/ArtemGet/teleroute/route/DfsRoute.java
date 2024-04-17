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

package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.Cmd;
import com.github.ArtemGet.teleroute.update.UpdWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Depth first search route.
 * Iterate over routes, pick first successful result.
 *
 * <p><img src="../doc-files/IteratorRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class DfsRoute<U, S> implements Route<U, S> {
    private final Collection<Route<U, S>> routes;

    /**
     * Construct IterateRoute that iterate over one or many routes.
     *
     * @param routes routes to iterate
     */
    @SafeVarargs
    public DfsRoute(final Route<U, S>... routes) {
        this(Arrays.asList(routes));
    }

    /**
     * Main constructor. Construct IterateRoute that iterate over many routes.
     *
     * @param routes routes to iterate
     */
    public DfsRoute(final Collection<Route<U, S>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        return Optional.ofNullable(update)
                .flatMap(
                        upd -> this.routes.stream()
                                .map(route -> route.route(upd))
                                .filter(Optional::isPresent)
                                .findFirst()
                                .orElse(Optional.empty())
                );
    }
}
