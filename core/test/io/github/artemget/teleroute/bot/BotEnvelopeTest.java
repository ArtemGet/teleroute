/*
 * MIT License
 *
 * Copyright (c) 2024-2026. Artem Getmanskii
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
 *
 */

package io.github.artemget.teleroute.bot;

import io.github.artemget.teleroute.command.FkCmd;
import io.github.artemget.teleroute.command.FkCmdErr;
import io.github.artemget.teleroute.route.RouteEnd;
import io.github.artemget.teleroute.send.FkClient;
import io.github.artemget.teleroute.send.FkSend;
import io.github.artemget.teleroute.send.FkSendErr;
import io.github.artemget.teleroute.update.FkWrap;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link BotEnvelope}.
 *
 * @since 2.0.0
 */
final class BotEnvelopeTest {

    @Test
    void handlesUpdateWhenRouteReturnsCommand() {
        final FkClient client = new FkClient();
        new BotEnvelope<>(client,
            new RouteEnd(
                new FkCmd(
                    new FkSend("response")
                )
            )
        ).handle(new FkWrap());
        MatcherAssert.assertThat(
            "Did not send response",
            client.sent(),
            Matchers.equalTo(java.util.Collections.singletonList("response"))
        );
    }

    @Test
    void handlesUpdateWhenRouteReturnsEmpty() {
        final FkClient client = new FkClient();
        new BotEnvelope<>(client,
            new RouteEnd(
                new FkCmd()))
            .handle(new FkWrap());
        MatcherAssert.assertThat(
            "Sent response when route returned empty",
            client.sent().isEmpty(),
            Matchers.is(true)
        );
    }

    @Test
    void throwsExceptionWhenCommandExecutionFails() {
        final BotEnvelope<String, FkClient> bot =
            new BotEnvelope<>(new FkClient(),
                new RouteEnd(
                    new FkCmdErr()
                ));
        Assertions.assertThrows(
            RuntimeException.class,
            () -> bot.handle(new FkWrap()),
            "Expected RuntimeException when command execution fails"
        );
    }

    @Test
    void throwsExceptionWhenSendFails() {
        final BotEnvelope<String, FkClient> bot =
            new BotEnvelope<>(
                new FkClient(),
                new RouteEnd(
                    new FkCmd(
                        new FkSendErr()
                    )
                )
            );
        Assertions.assertThrows(
            RuntimeException.class,
            () -> bot.handle(new FkWrap()),
            "Expected RuntimeException when send fails"
        );
    }
}
