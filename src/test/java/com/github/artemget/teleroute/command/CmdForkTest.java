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

package com.github.artemget.teleroute.command;

import com.github.artemget.teleroute.send.FkSend;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case {@link CmdFork}.
 *
 * @since 0.2.0
 */
final class CmdForkTest {

    @Test
    void executesOriginWhenNoError() throws CmdException {
        MatcherAssert.assertThat(
            "Executed spare command",
            new CmdFork<>(
                new FkCmd(new FkSend("origin")),
                new FkCmd(new FkSend("spare"))
            ).execute("").get(),
            Matchers.equalTo(new FkSend("origin"))
        );
    }

    @Test
    void executesSpareWhenError() throws CmdException {
        MatcherAssert.assertThat(
            "Executed target command",
            new CmdFork<>(
                new FkCmdErr(),
                new FkCmd(new FkSend("spare"))
            ).execute("").get(),
            Matchers.equalTo(new FkSend("spare"))
        );
    }
}
