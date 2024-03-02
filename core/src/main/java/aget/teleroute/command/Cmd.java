package aget.teleroute.command;


import aget.teleroute.send.Send;

import java.util.Optional;

/**
 * Command. Entrance to your business logic.
 * Feel free to implement.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public interface Cmd<SrcUpdate, Sender> {
    /**
     * Process update(business logic here), return Send as a result.
     *
     * @param update telegram update
     * @return Send
     */
    Optional<Send<Sender>> execute(SrcUpdate update);
}
