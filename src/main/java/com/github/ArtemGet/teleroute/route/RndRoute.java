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
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Pick any random route or command.
 *
 * <p><img src="../doc-files/RandomRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class RndRoute<U, S> implements Route<U, S> {
    private final Collection<Route<U, S>> routes;

    /**
     * Construct RandomRoute with one or many commands.
     *
     * @param commands one or many commands
     */
    @SafeVarargs
    public RndRoute(final Cmd<U, S>... commands) {
        this(
                Arrays.stream(commands)
                        .map(EndRoute::new)
                        .collect(Collectors.<Route<U, S>>toList())
        );
    }

    /**
     * Construct RandomRoute with one or many routes.
     *
     * @param routes one or many routes
     */
    @SafeVarargs
    public RndRoute(final Route<U, S>... routes) {
        this(Arrays.asList(routes));
    }

    /**
     * Main constructor. Construct RandomRoute with many routes.
     *
     * @param routes routes
     */
    public RndRoute(final Collection<Route<U, S>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        return routes.stream()
                .skip(new Random().nextInt(routes.size()))
                .findFirst()
                .flatMap(route -> route.route(update));
    }
}
