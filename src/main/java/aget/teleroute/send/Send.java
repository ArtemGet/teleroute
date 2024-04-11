package aget.teleroute.send;

/**
 * Sends messages via sender.
 * Feel free to implement.
 *
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public interface Send<S> {
    /**
     * Sends command result to chat or user.
     *
     * @param send Sender
     */
    void send(S send);
}
