package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.Cmd;
import com.github.ArtemGet.teleroute.update.UpdWrap;

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
     * @param update update wrapper, provide data required by routes and matches.
     * @return Cmd command
     */
    Optional<Cmd<U, S>> route(UpdWrap<U> update);
}
