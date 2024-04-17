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

package com.github.artemget.teleroute.send;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class MultiSendTest {

    @Test
    public void send_shouldSingle_whenSubmittedSingle() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(new FkSend("resp")).send(fkClient);

        Assertions.assertEquals(
                List.of("resp"),
                fkClient.sent()
        );
    }

    @Test
    public void send_shouldMulti_whenSubmittedMulti() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(
                new FkSend("resp1"),
                new FkSend("resp2"),
                new FkSend("resp3")
        ).send(fkClient);

        Assertions.assertEquals(
                List.of("resp1", "resp2", "resp3"),
                fkClient.sent()
        );
    }

    @Test
    public void send_shouldNotSend_whenError() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(new FkSendErr()).send(fkClient);

        Assertions.assertEquals(
                List.of(),
                fkClient.sent()
        );
    }

    @Test
    public void send_shouldSendOnlyOne_whenManyError() {
        FkClient fkClient = new FkClient();
        new MultiSend<>(
                new FkSendErr(),
                new FkSendErr(),
                new FkSend("resp")
        ).send(fkClient);

        Assertions.assertEquals(
                List.of("resp"),
                fkClient.sent()
        );
    }
}
