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
import com.github.artemget.teleroute.command.FkCmd;
import com.github.artemget.teleroute.send.FkClient;
import com.github.artemget.teleroute.send.FkSend;
import com.github.artemget.teleroute.update.FkUpdWrap;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link RndRoute}.
 *
 * @since 0.1.0
 */
final class RndRouteTest {
    /**
     * Command response content.
     */
    private static final String RESP = "resp";

    /**
     * Set of fake commands.
     */
    private static final Set<Cmd<String, FkClient>> CMD_SET =
        Set.of(new FkCmd(), new FkCmd(new FkSend(RndRouteTest.RESP)));

    @Test
    void shouldRouteAnyWhenManyCmdSpecified() {
        Assertions.assertTrue(
            CMD_SET.contains(
                new RndRoute<>(
                    new FkCmd(),
                    new FkCmd(new FkSend(RndRouteTest.RESP))
                ).route(new FkUpdWrap()).get()
            )
        );
    }

    @Test
    void shouldRouteAnyWhenManyRouteSpecified() {
        Assertions.assertTrue(
            CMD_SET.contains(
                new RndRoute<>(
                    new EndRoute<>(new FkCmd()),
                    new EndRoute<>(new FkCmd(new FkSend(RndRouteTest.RESP)))
                ).route(new FkUpdWrap()).get()
            )
        );
    }

    @Test
    void shouldRouteWhenOneCmdSpecified() {
        Assertions.assertEquals(
            new FkCmd(new FkSend(RndRouteTest.RESP)),
            new RndRoute<>(
                new FkCmd(new FkSend(RndRouteTest.RESP))
            ).route(new FkUpdWrap()).get()
        );
    }

    @Test
    void shouldRouteOneWhenOneRouteSpecified() {
        Assertions.assertEquals(
            new FkCmd(new FkSend(RndRouteTest.RESP)),
            new RndRoute<>(
                new EndRoute<>(new FkCmd(new FkSend(RndRouteTest.RESP)))
            ).route(new FkUpdWrap()).get()
        );
    }
}