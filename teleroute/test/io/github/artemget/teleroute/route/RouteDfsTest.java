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

import io.github.artemget.teleroute.command.FkCmd;
import io.github.artemget.teleroute.send.FkClient;
import io.github.artemget.teleroute.send.FkSend;
import io.github.artemget.teleroute.update.FkWrap;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link RouteDfs}.
 *
 * @since 0.1.0
 */
final class RouteDfsTest {

    @Test
    void routesNotWhenEmpty() {
        MatcherAssert.assertThat(
            "Routes with no commands",
            new RouteDfs<String, FkClient>(new RouteEnd<>())
                .route(new FkWrap())
                .isEmpty()
        );
    }

    @Test
    void routesNotWhenNullUpdate() {
        MatcherAssert.assertThat(
            "Routes with corrupted update",
            new RouteDfs<>(new RouteEnd<>(new FkCmd(new FkSend("resp"))))
                .route(null)
                .isEmpty()
        );
    }

    @Test
    void routesFirstWhenOneSubmitted() {
        MatcherAssert.assertThat(
            "Failed to route to single assigned command",
            new RouteDfs<>(new RouteEnd<>(new FkCmd(new FkSend("resp"))))
                .route(new FkWrap())
                .get(),
            Matchers.equalTo(new FkCmd(new FkSend("resp")))
        );
    }

    @Test
    void routesFirstSuitableWhenManySubmitted() {
        MatcherAssert.assertThat(
            "Routes to not first suitable command",
            new RouteDfs<>(
                new RouteEnd<>(),
                new RouteEnd<>(new FkCmd(new FkSend("resp1"))),
                new RouteEnd<>(new FkCmd(new FkSend("resp2")))
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend("resp1")))
        );
    }
}
