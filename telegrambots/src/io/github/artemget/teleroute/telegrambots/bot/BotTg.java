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

import io.github.artemget.teleroute.bot.BotEnvelope;
import io.github.artemget.teleroute.route.Route;
import io.github.artemget.teleroute.route.RouteDfs;
import io.github.artemget.teleroute.telegrambots.update.TgBotWrap;
import io.github.artemget.teleroute.update.Wrap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.cactoos.Proc;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * Telegram consumer template.
 *
 * @since 2.0.0
 */
public final class BotTg implements LongPollingSingleThreadUpdateConsumer {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Procedure for telegram update handling.
     */
    private final Proc<Wrap<Update>> bot;

    /**
     * Ctor.
     *
     * @param token Token
     * @param routes Routes
     */
    @SafeVarargs
    public BotTg(final String token, final Route<Update, TelegramClient>... routes) {
        this(new OkHttpTelegramClient(token), new RouteDfs<>(routes));
    }

    /**
     * Ctor.
     *
     * @param token Token
     * @param route Route
     */
    public BotTg(final String token, final Route<Update, TelegramClient> route) {
        this(new OkHttpTelegramClient(token), route);
    }

    /**
     * Ctor.
     *
     * @param client Client
     * @param routes Routes
     */
    @SafeVarargs
    public BotTg(final TelegramClient client, final Route<Update, TelegramClient>... routes) {
        this(new BotEnvelope<>(client, new RouteDfs<>(routes)));
    }

    /**
     * Ctor.
     *
     * @param client Client
     * @param route Route
     */
    public BotTg(final TelegramClient client, final Route<Update, TelegramClient> route) {
        this(new BotEnvelope<>(client, route));
    }

    /**
     * Main ctor.
     *
     * @param bot Bot
     */
    public BotTg(final Proc<Wrap<Update>> bot) {
        this.bot = bot;
    }

    //@checkstyle IllegalCatchCheck (8 lines)
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    @Override
    public void consume(final Update update) {
        try {
            this.bot.exec(new TgBotWrap(update));
        } catch (final Exception exception) {
            BotTg.LOGGER.error("Error occurred while processing update {}", update, exception);
        }
    }
}
