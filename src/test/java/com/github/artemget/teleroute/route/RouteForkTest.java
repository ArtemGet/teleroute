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

import com.github.artemget.teleroute.command.FkCmd;
import com.github.artemget.teleroute.match.FkMatch;
import com.github.artemget.teleroute.send.FkSend;
import com.github.artemget.teleroute.update.FkWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link RouteFork}.
 *
 * @since 0.1.0
 */
final class RouteForkTest {

    @Test
    void shouldRouteCmdWhenMatchAndNoSpareCmd() {
        Assertions.assertEquals(
            new FkCmd(new FkSend()),
            new RouteFork<>(
                new FkMatch(),
                new FkCmd(new FkSend())
            )
                .route(new FkWrap())
                .get()
        );
    }

    @Test
    void shouldRouteEmptyWhenNotMatchAndNoSpareCmd() {
        Assertions.assertTrue(
            new RouteFork<>(
                new FkMatch(false),
                new FkCmd(new FkSend())
            )
                .route(new FkWrap())
                .isEmpty()
        );
    }

    @Test
    void shouldRouteCmdWhenMatchAndSpareCmd() {
        Assertions.assertEquals(
            new FkCmd(new FkSend()),
            new RouteFork<>(
                new FkMatch(),
                new FkCmd(new FkSend()),
                new FkCmd()
            )
                .route(new FkWrap())
                .get()
        );
    }

    @Test
    void shouldRouteSpareCmdWhenNotMatchAndSpareCmd() {
        Assertions.assertEquals(
            new FkCmd(),
            new RouteFork<>(
                new FkMatch(false),
                new FkCmd(new FkSend()),
                new FkCmd()
            )
                .route(new FkWrap())
                .get()
        );
    }

    //routes

    @Test
    void shouldRouteWhenMatchAndNoSpareRoute() {
        Assertions.assertEquals(
            new FkCmd(new FkSend()),
            new RouteFork<>(
                new FkMatch(),
                new RouteEnd<>(new FkCmd(new FkSend()))
            )
                .route(new FkWrap())
                .get()
        );
    }

    @Test
    void shouldRouteEmptyWhenNotMatchAndNoSpareRoute() {
        Assertions.assertTrue(
            new RouteFork<>(
                new FkMatch(false),
                new RouteEnd<>(new FkCmd(new FkSend()))
            )
                .route(new FkWrap())
                .isEmpty()
        );
    }

    @Test
    void shouldRouteWhenMatchAndSpareRoute() {
        Assertions.assertEquals(
            new FkCmd(new FkSend()),
            new RouteFork<>(
                new FkMatch(),
                new RouteEnd<>(new FkCmd(new FkSend())),
                new RouteEnd<>(new FkCmd())
            )
                .route(new FkWrap())
                .get()
        );
    }

    @Test
    void shouldRouteSpareRouteWhenNotMatchAndSpareRoute() {
        Assertions.assertEquals(
            new FkCmd(),
            new RouteFork<>(
                new FkMatch(false),
                new RouteEnd<>(new FkCmd(new FkSend())),
                new RouteEnd<>(new FkCmd())
            )
                .route(new FkWrap())
                .get()
        );
    }
}
