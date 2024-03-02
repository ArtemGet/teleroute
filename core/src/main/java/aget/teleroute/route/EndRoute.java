package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.command.SkipCmd;
import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Optional;

public final class EndRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Cmd<SrcUpdate, Sender> deadEndCommand;

    public EndRoute() {
        this(new SkipCmd<>());
    }

    public EndRoute(final Cmd<SrcUpdate, Sender> deadEndCmd) {
        this.deadEndCommand = deadEndCmd;
    }

    @Override
    public Optional<Send<Sender>> route(Update<SrcUpdate> update) {
        return deadEndCommand.execute(update.source());
    }
}
