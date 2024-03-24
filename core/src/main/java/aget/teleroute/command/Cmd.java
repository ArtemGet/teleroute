package aget.teleroute.command;

import aget.teleroute.send.Send;

import java.util.Optional;

/**
 * Command. Entrance to your business logic.
 * Feel free to implement.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public interface Cmd<U, S> {
    /**
     * Process update(business logic here), return Send as a result.
     *
     * @param update telegram update
     * @return Send
     */
    Optional<Send<S>> execute(U update);
}
