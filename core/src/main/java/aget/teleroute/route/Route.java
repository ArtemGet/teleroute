package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.update.UpdWrap;

import java.util.Optional;

/**
 * Route, core interface. Routes update to command or other route. Return Cmd as a result.
 * Feel free to implement.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public interface Route<U, S> {
    /**
     * Routes update to command or other Route.
     *
     * @param updWrap update wrapper, provide data required by routes and matches.
     * @return Cmd command
     */
    Optional<Cmd<U, S>> route(UpdWrap<U> updWrap);
}
