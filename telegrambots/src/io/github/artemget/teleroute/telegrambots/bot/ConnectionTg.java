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

package io.github.artemget.teleroute.telegrambots.bot;

import io.github.artemget.teleroute.bot.Connection;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteDfs;
import java.io.IOException;
import org.cactoos.proc.CheckedProc;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Telegram Bot Connection.
 *
 * @since 2.0.0
 * @todo #41:30min After release jdk 26 change IOException to Exception
 *    for AutoCloseable interface, see
 *    <a href="https://bugs.openjdk.org/browse/JDK-8155591">SDK bug</a>
 */
public final class ConnectionTg implements Connection {

    /**
     * Bot token.
     */
    private final String token;

    /**
     * Consumer.
     */
    private final LongPollingUpdateConsumer consumer;

    /**
     * Application.
     */
    private final TelegramBotsLongPollingApplication application;

    /**
     * Ctor.
     *
     * @param token Token
     * @param routes Routes
     */
    @SafeVarargs
    public ConnectionTg(final String token, final Route<Update, TelegramClient>... routes) {
        this(token, new RouteDfs<>(routes));
    }

    /**
     * Ctor.
     *
     * @param token Token
     * @param route Route
     */
    public ConnectionTg(final String token, final Route<Update, TelegramClient> route) {
        this(token, new TelegramBotsLongPollingApplication(), new BotTg(token, route));
    }

    /**
     * Ctor.
     *
     * @param token Token
     * @param application Application
     * @param route Route
     */
    public ConnectionTg(
        final String token,
        final TelegramBotsLongPollingApplication application,
        final Route<Update, TelegramClient> route
    ) {
        this(token, application, new BotTg(token, route));
    }

    /**
     * Main ctor.
     *
     * @param token Token
     * @param application Application
     * @param consumer Consumer
     */
    public ConnectionTg(
        final String token,
        final TelegramBotsLongPollingApplication application,
        final LongPollingUpdateConsumer consumer
    ) {
        this.token = token;
        this.application = application;
        this.consumer = consumer;
    }

    @Override
    public void open() throws Exception {
        this.application.registerBot(this.token, this.consumer);
    }

    @Override
    public void close() throws IOException {
        new CheckedProc<>(
            TelegramBotsLongPollingApplication::close,
            ex -> new IOException("Failed to close Telegram Bot Connection", ex)
        ).exec(this.application);
    }
}
