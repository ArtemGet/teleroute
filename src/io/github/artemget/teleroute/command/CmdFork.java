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

import io.github.artemget.teleroute.send.Send;

/**
 * Fork command. Origin or spare if error.
 *
 * @param <U> Update
 * @param <C> Client
 * @since 0.2.0
 */
public final class CmdFork<U, C> implements Cmd<U, C> {
    /**
     * Origin route. Would be executed always.
     */
    private final Cmd<U, C> origin;

    /**
     * Spare route. Would be executed if error.
     */
    private final Cmd<U, C> spare;

    /**
     * Main ctor.
     *
     * @param origin Command
     * @param spare Command
     */
    public CmdFork(final Cmd<U, C> origin, final Cmd<U, C> spare) {
        this.origin = origin;
        this.spare = spare;
    }

    @Override
    public Send<C> execute(final U update) throws CmdException {
        Send<C> resp;
        try {
            resp = this.origin.execute(update);
        } catch (final CmdException exception) {
            resp = this.spare.execute(update);
        }
        return resp;
    }
}
