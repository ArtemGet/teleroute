/*
 * MIT License
 *
 * Copyright (c) 2024-2026. Artem Getmanskii
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
 *
 */

package io.github.artemget.teleroute.bot;

import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteDfs;
import io.github.artemget.teleroute.update.Wrap;
import org.cactoos.proc.CheckedProc;
import org.cactoos.proc.UncheckedProc;
import org.cactoos.scalar.Unchecked;

/**
 * Bot envelope helps create bot signature.
 *
 * @param <U> Update
 * @param <C> Client
 * @since 2.0.0
 */
public final class BotEnvelope<U, C> implements Bot<U> {
    /**
     * Client.
     */
    private final C client;

    /**
     * Route.
     */
    private final Route<U, C> route;

    /**
     * Secondary constructor.
     *
     * @param client Client
     * @param routes Routes
     */
    @SafeVarargs
    public BotEnvelope(final C client, final Route<U, C>... routes) {
        this(client, new RouteDfs<>(routes));
    }

    /**
     * Main constructor.
     *
     * @param client Client
     * @param route  Route
     */
    public BotEnvelope(final C client, final Route<U, C> route) {
        this.client = client;
        this.route = route;
    }

    @Override
    public void handle(final Wrap<U> update) throws Exception {
        new CheckedProc<>(
            (Wrap<U> u) -> this.route.route(update)
                .map(
                    cmd -> new Unchecked<>(() -> cmd.execute(update.src())).value()
                ).ifPresent(send -> new UncheckedProc<>(send::send).exec(this.client)),
            ex -> new Exception("Failed to handle update", ex)
        ).exec(update);
    }
}
