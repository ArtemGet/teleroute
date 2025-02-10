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

package io.github.artemget.teleroute.route;

import io.github.artemget.teleroute.command.Cmd;
import io.github.artemget.teleroute.update.Wrap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Pick any random route.
 *
 * <p><img src="../doc-files/RandomRouteScheme.png" width=1000>
 *
 * @param <U> Update
 * @param <C> Client
 * @since 0.1.0
 */
public final class RouteRnd<U, C> implements Route<U, C> {
    /**
     * Routes.
     */
    private final Collection<Route<U, C>> routes;

    /**
     * Ctor.
     *
     * @param commands Commands
     */
    @SafeVarargs
    public RouteRnd(final Cmd<U, C>... commands) {
        this(
            Arrays.stream(commands)
                .map(RouteEnd::new)
                .collect(Collectors.<Route<U, C>>toList())
        );
    }

    /**
     * Ctor.
     *
     * @param routes Routes
     */
    @SafeVarargs
    public RouteRnd(final Route<U, C>... routes) {
        this(Arrays.asList(routes));
    }

    /**
     * Main ctor.
     *
     * @param routes Routes
     */
    public RouteRnd(final Collection<Route<U, C>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Cmd<U, C>> route(final Wrap<U> update) {
        return this.routes.stream()
            .skip(new Random().nextInt(this.routes.size()))
            .findFirst()
            .flatMap(route -> route.route(update));
    }
}
