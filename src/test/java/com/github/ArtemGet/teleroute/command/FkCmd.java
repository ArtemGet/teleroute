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

package com.github.ArtemGet.teleroute.command;

import com.github.ArtemGet.teleroute.send.FkClient;
import com.github.ArtemGet.teleroute.send.Send;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FkCmd implements Cmd<String, FkClient> {
    private final List<Send<FkClient>> send;

    public FkCmd() {
        send = Collections.emptyList();
    }

    public FkCmd(Send<FkClient> send) {
        this.send = Collections.singletonList(send);
    }

    @Override
    public Optional<Send<FkClient>> execute(String update) {
        if (send.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(send.get(0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FkCmd fkCmd = (FkCmd) o;
        return Objects.equals(send, fkCmd.send);
    }

    @Override
    public int hashCode() {
        return Objects.hash(send);
    }

    @Override
    public String toString() {
        return "FkCmd{" +
                "send=" + send +
                '}';
    }
}
