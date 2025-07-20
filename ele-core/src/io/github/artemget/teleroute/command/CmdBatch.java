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
import io.github.artemget.teleroute.send.SendBatch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Execute many commands as one.
 *
 * @param <U> Update
 * @param <C> Client
 * @since 0.1.0
 */
public final class CmdBatch<U, C> implements Cmd<U, C> {

    /**
     * Commands.
     */
    private final Collection<Cmd<U, C>> commands;

    /**
     * Ctor.
     *
     * @param commands Commands to execute
     */
    @SafeVarargs
    public CmdBatch(final Cmd<U, C>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Main ctor.
     *
     * @param commands Commands to execute
     */
    public CmdBatch(final Collection<Cmd<U, C>> commands) {
        this.commands = Collections.unmodifiableCollection(commands);
    }

    @Override
    public Send<C> execute(final U update) throws CmdException {
        final List<Send<C>> sends = this.executeCmds(update);
        final Send<C> resp;
        if (sends.isEmpty()) {
            resp = new Send.Void<>();
        } else {
            resp = new SendBatch<>(sends);
        }
        return resp;
    }

    private List<Send<C>> executeCmds(final U update) throws CmdException {
        final List<Send<C>> sends = new ArrayList<>(this.commands.size());
        for (final Cmd<U, C> cmd : this.commands) {
            sends.add(cmd.execute(update));
        }
        return sends;
    }
}
