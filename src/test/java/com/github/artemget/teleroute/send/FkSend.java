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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Fake send.
 *
 * @since 0.1.0
 */
public final class FkSend implements Send<FkClient> {
    /**
     * Response that would be sent by client.
     */
    private final List<String> response;

    public FkSend() {
        this(Collections.emptyList());
    }

    public FkSend(final String... content) {
        this(Arrays.asList(content));
    }

    public FkSend(final List<String> content) {
        this.response = Collections.unmodifiableList(content);
    }

    @Override
    public void send(final FkClient client) {
        this.response.forEach(client::submit);
    }

    @Override
    public boolean equals(final Object object) {
        return this == object
            || object instanceof FkSend && this.response.equals(((FkSend) object).response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.response);
    }
}
