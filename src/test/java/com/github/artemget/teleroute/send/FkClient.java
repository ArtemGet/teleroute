/*
 * MIT License
 *
 * Copyright (c) 2024 Artem Getmanskii
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Fake tg client.
 *
 * @since 0.0.0
 */
public final class FkClient {
    /**
     * Response sent by client.
     */
    private final List<String> response;

    /**
     * Constructs empty response.
     */
    public FkClient() {
        this(new ArrayList<>(1));
    }

    /**
     * Constructs response containing single content.
     *
     * @param message Response content
     */
    public FkClient(final String message) {
        this(Collections.singletonList(message));
    }

    /**
     * Main constructor.
     *
     * @param message Response content
     */
    private FkClient(final List<String> message) {
        this.response = message;
    }

    /**
     * Submit content to tg response.
     *
     * @param message Content
     */
    public void submit(final String message) {
        this.response.add(message);
    }

    /**
     * Retrieve tg response contents.
     *
     * @return Response
     */
    public List<String> sent() {
        return Collections.unmodifiableList(this.response);
    }
}
