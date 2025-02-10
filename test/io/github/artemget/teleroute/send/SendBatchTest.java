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

package io.github.artemget.teleroute.send;

import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link SendBatch}.
 *
 * @since 0.1.0
 */
final class SendBatchTest {

    @Test
    void executesSingleWhenSubmittedSingle() throws SendException {
        final FkClient client = new FkClient();
        new SendBatch<>(new FkSend("resp")).send(client);
        MatcherAssert.assertThat(
            "Single submitted didnt execute",
            client.sent(),
            Matchers.equalTo(List.of("resp"))
        );
    }

    @Test
    void executesMultiWhenSubmittedMulti() throws SendException {
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
    void throwsWhenError() {
        Assertions.assertThrows(
            SendException.class,
            () -> new SendBatch<>(new FkSendErr()).send(new FkClient()),
            "Sent while error occurred"
        );
    }

    @Test
    void sendsNothingWhenManyErrorBefore() {
        final FkClient client = new FkClient();
        try {
            new SendBatch<>(
                new FkSendErr(),
                new FkSendErr(),
                new FkSend("resp")
            ).send(client);
        } catch (final SendException exception) {
            //ignore
        }
        MatcherAssert.assertThat(
            "Sent succeed command",
            client.sent(),
            Matchers.equalTo(List.of())
        );
    }

    @Test
    void sendsSucceedWhenErrorAfter() {
        final FkClient client = new FkClient();
        try {
            new SendBatch<>(
                new FkSend("resp"),
                new FkSendErr()
            ).send(client);
        } catch (final SendException exception) {
            //ignore
        }
        MatcherAssert.assertThat(
            "Sent succeed command",
            client.sent(),
            Matchers.equalTo(List.of("resp"))
        );
    }

    @Test
    void equalsWhenSameObject() {
        final Send<String> batch = new SendBatch<>();
        MatcherAssert.assertThat(
            "SendBatch not equal to itself",
            batch,
            Matchers.equalTo(batch)
        );
    }

    @Test
    void equalsWhenSameFilledObject() {
        final Send<String> batch = new SendBatch<>(new Send.Not<>());
        MatcherAssert.assertThat(
            "SendBatch not equal to itself",
            batch,
            Matchers.equalTo(batch)
        );
    }

    @Test
    void equalsWhenDifferentFilledObject() {
        MatcherAssert.assertThat(
            "SendBatch not equal to same object",
            new SendBatch<>(new Send.Not<>()),
            Matchers.equalTo(new SendBatch<>(new Send.Not<>()))
        );
    }

    @Test
    void equalsNotWhenDifferentFilledObject() {
        MatcherAssert.assertThat(
            "SendBatch equals to different object",
            new SendBatch<>(),
            Matchers.not(new SendBatch<>(new Send.Not<>()))
        );
    }

    @Test
    void equalsNotWhenNull() {
        MatcherAssert.assertThat(
            "SendBatch equals to null",
            !new SendBatch<>().equals(new Object())
        );
    }

    @Test
    void matchesHash() {
        MatcherAssert.assertThat(
            "SendBatch equals to different object",
            new SendBatch<>(new Send.Not<>()).hashCode(),
            Matchers.equalTo(new SendBatch<>(new Send.Not<>()).hashCode())
        );
    }
}
