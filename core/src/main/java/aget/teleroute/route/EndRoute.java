package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.update.UpdWrap;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Route to single command or none.
 *
 * <p><img src="../doc-files/EndRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class EndRoute<U, S> implements Route<U, S> {
    private final Collection<Cmd<U, S>> deadEndCommand;

    /**
     * Construct EndRoute that actually do nothing.
     */
    public EndRoute() {
        this.deadEndCommand = Collections.emptyList();
    }

    /**
     * Main constructor. Construct EndRoute that route to command.
     *
     * @param deadEndCmd command
     */
    public EndRoute(final Cmd<U, S> deadEndCmd) {
        this.deadEndCommand = Collections.singletonList(deadEndCmd);
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> updWrap) {
        return this.deadEndCommand.stream().findFirst();
    }
}
