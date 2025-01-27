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

import com.github.artemget.teleroute.command.FkCmd;
import com.github.artemget.teleroute.match.FkMatch;
import com.github.artemget.teleroute.send.FkSend;
import com.github.artemget.teleroute.update.FkWrap;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link RouteFork}.
 *
 * @since 0.1.0
 */
final class RouteForkTest {

    @Test
    void routesToCmdWhenMatchAndNoSpareCmd() {
        MatcherAssert.assertThat(
            "Didnt route to matched command",
            new RouteFork<>(
                new FkMatch(),
                new FkCmd(new FkSend())
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend()))
        );
    }

    @Test
    void routesNotWhenNotMatchAndNoSpareCmd() {
        MatcherAssert.assertThat(
            "Routes to command that not matched",
            new RouteFork<>(
                new FkMatch(false),
                new FkCmd(new FkSend())
            ).route(new FkWrap()).isEmpty()
        );
    }

    @Test
    void routesCmdWhenMatchAndSpareCmd() {
        MatcherAssert.assertThat(
            "Routes to command that not matched",
            new RouteFork<>(
                new FkMatch(),
                new FkCmd(new FkSend()),
                new FkCmd()
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend()))
        );
    }

    @Test
    void routesSpareCmdWhenNotMatchAndSpareCmd() {
        MatcherAssert.assertThat(
            "Routes to command that not matched",
            new RouteFork<>(
                new FkMatch(false),
                new FkCmd(new FkSend()),
                new FkCmd()
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd())
        );
    }

    //routes

    @Test
    void routesWhenMatchAndNoSpareRoute() {
        MatcherAssert.assertThat(
            "Didnt route to matched command",
            new RouteFork<>(
                new FkMatch(),
                new RouteEnd<>(new FkCmd(new FkSend()))
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend()))
        );
    }

    @Test
    void routesEmptyWhenNotMatchAndNoSpareRoute() {
        MatcherAssert.assertThat(
            "Routes to command that not matched",
            new RouteFork<>(
                new FkMatch(false),
                new RouteEnd<>(new FkCmd(new FkSend()))
            ).route(new FkWrap()).isEmpty()
        );
    }

    @Test
    void routesWhenMatchAndSpareRoute() {
        MatcherAssert.assertThat(
            "Didnt route to matched command",
            new RouteFork<>(
                new FkMatch(),
                new RouteEnd<>(new FkCmd(new FkSend())),
                new RouteEnd<>(new FkCmd())
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend()))
        );
    }

    @Test
    void routesSpareRouteWhenNotMatchAndSpareRoute() {
        MatcherAssert.assertThat(
            "Didnt route to matched command",
            new RouteFork<>(
                new FkMatch(false),
                new RouteEnd<>(new FkCmd(new FkSend())),
                new RouteEnd<>(new FkCmd())
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd())
        );
    }
}
