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
import io.github.artemget.teleroute.send.Send;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Fake Command.
 *
 * @since 0.1.0
 */
@SuppressWarnings({"JTCOP.RuleAllTestsHaveProductionClass", "JTCOP.RuleCorrectTestName"})
public final class FkCmd implements Cmd<String, FkClient> {
    /**
     * Client.
     */
    private final List<Send<FkClient>> send;

    public FkCmd() {
        this(Collections.emptyList());
    }

    public FkCmd(final Send<FkClient> client) {
        this(Collections.singletonList(client));
    }

    public FkCmd(final List<Send<FkClient>> clients) {
        this.send = Collections.unmodifiableList(clients);
    }

    @Override
    public Send<FkClient> execute(final String update) {
        final Send<FkClient> resp;
        if (this.send.isEmpty()) {
            resp = new Send.Not<>();
        } else {
            resp = this.send.get(0);
        }
        return resp;
    }

    @Override
    public boolean equals(final Object object) {
        final FkCmd cmd = (FkCmd) object;
        return this == object
            || object == null || !this.getClass().equals(object.getClass())
            || Objects.equals(this.send, cmd.send);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.send);
    }
}
