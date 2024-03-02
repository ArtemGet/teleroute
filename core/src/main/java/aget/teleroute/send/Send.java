package aget.teleroute.send;

/**
 * <p></p>
 * Sends messages via sender.
 * Feel free to implement.
 *
 * @param <Sender> Sender, actually sends messages, ie AdsSender from telegrambots or your own impl of tg send**
 */
public interface Send<Sender> {
    /**
     * Send command result to chat or user.
     *
     * @param send Sender
     */
    void send(Sender send);
}
