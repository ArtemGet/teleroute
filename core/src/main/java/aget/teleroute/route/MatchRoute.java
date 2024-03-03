package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.command.SkipCmd;
import aget.teleroute.match.Match;
import aget.teleroute.send.Send;
import aget.teleroute.update.UpdateWrap;

import java.util.Optional;

/**
 * Routes to route or command in case of update matching condition, if not - route to other route or command or none.
 *
 * <p><img src="../doc-files/MatchRouteScheme.png" width=1000>
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class MatchRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Route<SrcUpdate, Sender> route;
    private final Route<SrcUpdate, Sender> spareRoute;
    private final Match<SrcUpdate> match;

    /**
     * Construct MatchRoute that routes to match command or none if update not match condition.
     *
     * @param match   match condition
     * @param command match command
     */
    public MatchRoute(final Match<SrcUpdate> match,
                      final Cmd<SrcUpdate, Sender> command) {
        this(match, command, new SkipCmd<>());
    }

    /**
     * Construct MatchRoute that route to match command or spare command if update not match condition.
     *
     * @param match           match condition
     * @param command         match command
     * @param spareRoute spare command
     */
    public MatchRoute(final Match<SrcUpdate> match,
                      final Cmd<SrcUpdate, Sender> command,
                      final Cmd<SrcUpdate, Sender> spareRoute) {
        this(match, new EndRoute<>(command), new EndRoute<>(spareRoute));
    }

    /**
     * Construct MatchRoute that route to match route or none if update not match condition.
     *
     * @param match match condition
     * @param route match route
     */
    public MatchRoute(final Match<SrcUpdate> match,
                      final Route<SrcUpdate, Sender> route) {
        this(match, route, new EndRoute<>());
    }

    /**
     * Main constructor. Construct MatchRoute that route to match route or spare route if update not match condition.
     *
     * @param match         match condition
     * @param route         match route
     * @param spareRoute spare route
     */
    public MatchRoute(final Match<SrcUpdate> match,
                      final Route<SrcUpdate, Sender> route,
                      final Route<SrcUpdate, Sender> spareRoute) {
        this.match = match;
        this.route = route;
        this.spareRoute = spareRoute;
    }

    @Override
    public Optional<Send<Sender>> route(final UpdateWrap<SrcUpdate> updateWrap) {
        if (!match.match(updateWrap)) {
            return spareRoute.route(updateWrap);
        }

        return this.route.route(updateWrap);
    }
}
