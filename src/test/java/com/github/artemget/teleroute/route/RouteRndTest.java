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

import com.github.artemget.teleroute.command.Cmd;
import com.github.artemget.teleroute.command.FkCmd;
import com.github.artemget.teleroute.send.FkClient;
import com.github.artemget.teleroute.send.FkSend;
import com.github.artemget.teleroute.update.FkWrap;
import java.util.Set;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link RouteRnd}.
 *
 * @since 0.1.0
 */
final class RouteRndTest {
    /**
     * Command response content.
     */
    private static final String RESP = "resp";

    /**
     * Set of fake commands.
     */
    private static final Set<Cmd<String, FkClient>> CMD_SET =
        Set.of(new FkCmd(), new FkCmd(new FkSend(RouteRndTest.RESP)));

    @Test
    void routesAnyWhenManyCmdSpecified() {
        MatcherAssert.assertThat(
            "Nothing in return",
            CMD_SET.contains(
                new RouteRnd<>(
                    new FkCmd(),
                    new FkCmd(new FkSend(RouteRndTest.RESP))
                ).route(new FkWrap()).get()
            )
        );
    }

    @Test
    void routesAnyWhenManyRouteSpecified() {
        MatcherAssert.assertThat(
            "Nothing in return",
            CMD_SET.contains(
                new RouteRnd<>(
                    new RouteEnd<>(new FkCmd()),
                    new RouteEnd<>(new FkCmd(new FkSend(RouteRndTest.RESP)))
                ).route(new FkWrap()).get()
            )
        );
    }

    @Test
    void routesWhenOneCmdSpecified() {
        MatcherAssert.assertThat(
            "Noting returned when only one command available",
            new RouteRnd<>(
                new FkCmd(new FkSend(RouteRndTest.RESP))
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend(RouteRndTest.RESP)))
        );
    }

    @Test
    void routesOneWhenOneRouteSpecified() {
        MatcherAssert.assertThat(
            "Noting returned when only one route available",
            new RouteRnd<>(
                new RouteEnd<>(new FkCmd(new FkSend(RouteRndTest.RESP)))
            ).route(new FkWrap()).get(),
            Matchers.equalTo(new FkCmd(new FkSend(RouteRndTest.RESP)))
        );
    }
}
