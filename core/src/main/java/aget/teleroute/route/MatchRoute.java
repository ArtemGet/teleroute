package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.command.SkipCmd;
import aget.teleroute.match.Match;
import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Optional;

public final class MatchRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Route<SrcUpdate, Sender> route;
    private final Route<SrcUpdate, Sender> notMatchRoute;
    private final Match<SrcUpdate> match;

    public MatchRoute(final Match<SrcUpdate> match,
                      final Cmd<SrcUpdate, Sender> command) {
        this(match, command, new SkipCmd<>());
    }

    public MatchRoute(final Match<SrcUpdate> match,
                      final Cmd<SrcUpdate, Sender> command,
                      final Cmd<SrcUpdate, Sender> notMatchCommand) {
        this(match, new EndRoute<>(command), new EndRoute<>(notMatchCommand));
    }

    public MatchRoute(final Match<SrcUpdate> match,
                      final Route<SrcUpdate, Sender> route) {
        this(match, route, new EndRoute<>());
    }

    public MatchRoute(final Match<SrcUpdate> match,
                      final Route<SrcUpdate, Sender> route,
                      final Route<SrcUpdate, Sender> notMatchRoute) {
        this.match = match;
        this.route = route;
        this.notMatchRoute = notMatchRoute;
    }

    @Override
    public Optional<Send<Sender>> route(Update<SrcUpdate> update) {
        if (!match.match(update)) {
            return notMatchRoute.route(update);
        }

        return this.route.route(update);
    }
}
