package aget.teleroute.route;

import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Optional;

/**
 * Route, core interface. Routes Update to command or other Route. Return Send as a result.
 * Feel free to implement.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public interface Route<SrcUpdate, Sender> {
    /**
     * Routes Update to command or other Route.
     *
     * @param update update wrapper, provide data required by routes and matches.
     * @return Send
     */
    Optional<Send<Sender>> route(Update<SrcUpdate> update);
}
