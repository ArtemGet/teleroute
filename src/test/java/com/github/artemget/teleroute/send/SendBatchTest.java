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

package com.github.artemget.teleroute.send;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link SendBatch}.
 *
 * @since 0.1.0
 */
final class SendBatchTest {

    @Test
    void executesSingleWhenSubmittedSingle() {
        final FkClient client = new FkClient();
        new SendBatch<>(new FkSend("resp")).send(client);
        MatcherAssert.assertThat(
            "Single submitted didnt execute",
            client.sent(),
            Matchers.equalTo(List.of("resp"))
        );
    }

    @Test
    void executesMultiWhenSubmittedMulti() {
        final FkClient client = new FkClient();
        new SendBatch<>(
            new FkSend("resp1"),
            new FkSend("resp2"),
            new FkSend("resp3")
        ).send(client);
        MatcherAssert.assertThat(
            "Didnt execute all commands",
            client.sent(),
            Matchers.equalTo(List.of("resp1", "resp2", "resp3"))
        );
    }

    @Test
    void sendsNotWhenError() {
        final FkClient client = new FkClient();
        new SendBatch<>(new FkSendErr()).send(client);
        MatcherAssert.assertThat(
            "Sent while error occurred",
            client.sent(),
            Matchers.equalTo(List.of())
        );
    }

    @Test
    void sendsOnlyOneWhenManyError() {
        final FkClient client = new FkClient();
        new SendBatch<>(
            new FkSendErr(),
            new FkSendErr(),
            new FkSend("resp")
        ).send(client);
        MatcherAssert.assertThat(
            "Didnt send succeed command",
            client.sent(),
            Matchers.equalTo(List.of("resp"))
        );
    }
}
