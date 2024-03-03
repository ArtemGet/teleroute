package aget.teleroute.send;

/**
 * Sends messages via sender.
 * Feel free to implement.
 *
 * @param <Sender> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public interface Send<Sender> {
    /**
     * Sends command result to chat or user.
     *
     * @param send Sender
     */
    void send(Sender send);
}
