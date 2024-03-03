package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.command.SkipCmd;
import aget.teleroute.send.Send;
import aget.teleroute.update.UpdateWrap;

import java.util.Optional;

/**
 * Route to single command or none.
 *
 * <p><img src="../doc-files/EndRouteScheme.png" width=1000>
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class EndRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Cmd<SrcUpdate, Sender> deadEndCommand;

    /**
     * Construct EndRoute that actually do nothing.
     */
    public EndRoute() {
        this(new SkipCmd<>());
    }

    /**
     * Main constructor. Construct EndRoute that route to command.
     *
     * @param deadEndCmd command
     */
    public EndRoute(final Cmd<SrcUpdate, Sender> deadEndCmd) {
        this.deadEndCommand = deadEndCmd;
    }

    @Override
    public Optional<Send<Sender>> route(final UpdateWrap<SrcUpdate> updateWrap) {
        return deadEndCommand.execute(updateWrap.source());
    }
}
