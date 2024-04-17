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
import com.github.artemget.teleroute.send.FkClient;
import com.github.artemget.teleroute.send.FkSend;
import com.github.artemget.teleroute.update.FkUpdWrap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DfsRouteTest {

    @Test
    void route_shouldRouteEmpty_whenEmpty() {
        Assertions.assertTrue(
                new DfsRoute<String, FkClient>(new EndRoute<>())
                        .route(new FkUpdWrap())
                        .isEmpty()
        );
    }

    @Test
    void route_shouldRouteEmpty_whenNullUpdate() {
        Assertions.assertTrue(
                new DfsRoute<>(new EndRoute<>(new FkCmd(new FkSend("resp"))))
                        .route(null)
                        .isEmpty()
        );
    }

    @Test
    void route_shouldRouteFirst_whenOneSubmitted() {
        Assertions.assertEquals(
                new FkCmd(new FkSend("resp")),
                new DfsRoute<>(new EndRoute<>(new FkCmd(new FkSend("resp"))))
                        .route(new FkUpdWrap())
                        .get()
        );
    }

    @Test
    void route_shouldRouteFirstSuitable_whenManySubmitted() {
        Assertions.assertEquals(
                new FkCmd(new FkSend("resp1")),
                new DfsRoute<>(
                        new EndRoute<>(),
                        new EndRoute<>(new FkCmd(new FkSend("resp1"))),
                        new EndRoute<>(new FkCmd(new FkSend("resp2")))
                )
                .route(new FkUpdWrap())
                .get()
        );
    }
}
