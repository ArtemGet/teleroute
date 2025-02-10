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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

/**
 * Sends many commands as one.
 *
 * @param <C> Client
 * @since 0.1.0
 */
public final class SendBatch<C> implements Send<C> {

    /**
     * Sends.
     */
    private final Collection<Send<C>> sends;

    /**
     * Ctor.
     *
     * @param sends Sends
     */
    @SafeVarargs
    public SendBatch(final Send<C>... sends) {
        this(Arrays.asList(sends));
    }

    /**
     * Main ctor.
     *
     * @param sends Sends
     */
    public SendBatch(final Collection<Send<C>> sends) {
        this.sends = Collections.unmodifiableCollection(sends);
    }

    @Override
    public void send(final C client) throws SendException {
        for (final Send<C> send : this.sends) {
            send.send(client);
        }
    }

    @Override
    public boolean equals(final Object obj) {
        final boolean equals;
        if (this == obj) {
            equals = true;
        } else if (obj == null || this.getClass() != obj.getClass()) {
            equals = false;
        } else {
            final SendBatch<?> batch = (SendBatch<?>) obj;
            equals = this.sends.stream()
                .toList()
                .equals(batch.sends.stream().toList());
        }
        return equals;
    }

    @Override
    public int hashCode() {
        return Objects.hash(new ArrayList<>(this.sends));
    }
}
