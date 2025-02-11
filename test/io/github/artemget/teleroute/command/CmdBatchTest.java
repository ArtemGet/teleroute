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

package io.github.artemget.teleroute.command;

import io.github.artemget.teleroute.send.FkClient;
import io.github.artemget.teleroute.send.FkSend;
import io.github.artemget.teleroute.send.Send;
import io.github.artemget.teleroute.send.SendException;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link CmdBatch}.
 *
 * @since 0.1.0
 */
final class CmdBatchTest {

    @Test
    void sendsOneWhenSubmitOne() throws CmdException, SendException {
        final FkClient client = new FkClient();
        new CmdBatch<>(
            new FkCmd(new FkSend("resp"))
        ).execute("resp").send(client);
        MatcherAssert.assertThat(
            "Didnt send submitted command",
            client.sent(),
            Matchers.equalTo(List.of("resp"))
        );
    }

    @Test
    void sendsManyWhenSubmitMany() throws CmdException, SendException {
        final FkClient client = new FkClient();
        new CmdBatch<>(
            new FkCmd(new FkSend("resp1")),
            new FkCmd(new FkSend("resp2"))
        ).execute("resp").send(client);
        MatcherAssert.assertThat(
            "Didnt send all submitted commands",
            client.sent(),
            Matchers.equalTo(List.of("resp1", "resp2"))
        );
    }

    @Test
    void throwsWhenError() {
        Assertions.assertThrows(
            CmdException.class,
            () -> new CmdBatch<>(new FkCmdErr())
                .execute("resp"),
            "Sent command while error occurred"
        );
    }

    @Test
    void sendsNotWhenNothingToSend() throws CmdException {
        MatcherAssert.assertThat(
            "Sent command while error occurred",
            new CmdBatch<>().execute("resp"),
            Matchers.equalTo(new Send.Void<>())
        );
    }
}
