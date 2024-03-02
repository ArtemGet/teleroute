//TODO: implement wraps to all possible types that AbsSender could execute
package aget.teleroute.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.Serializable;

//TODO: write down all actual ancestors of BotApiMethod<T> in current telegrambots version

/**
 * Send all content laying under BotApiMethod<T> including ancestors of
 * BotApiMethod<Boolean>, BotApiMethod<Message>, BotApiMethod<Serializable>.
 *
 * @param <T> generic param of BotApiMethod - Boolean, Message, Serializable
 */
public class SendMessageWrap<T extends Serializable> implements Send<AbsSender> {
    private static final Logger log = LoggerFactory.getLogger(SendMessageWrap.class);
    private final BotApiMethod<T> sendMessage;

    /**
     * Main constructor. Construct SendMessageWrap.
     *
     * @param sendMessage telegrambots BotApiMethod<T>
     */
    public SendMessageWrap(BotApiMethod<T> sendMessage) {
        this.sendMessage = sendMessage;
    }

    @Override
    public void send(AbsSender send) {
        try {
            send.execute(this.sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }
}
