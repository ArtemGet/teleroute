package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.match.Match;
import aget.teleroute.update.UpdWrap;

import java.util.Optional;

/**
 * Fork route.
 * Routes to route or command in case of update matching condition, if not - route to other route or command or none.
 *
 * <p><img src="../doc-files/MatchRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class FkRoute<U, S> implements Route<U, S> {
    private final Route<U, S> route;
    private final Route<U, S> spareRoute;
    private final Match<U> match;

    /**
     * Construct FkRoute that routes to match command or none if update not match condition.
     *
     * @param match   match condition
     * @param command match command
     */
    public FkRoute(final Match<U> match,
                   final Cmd<U, S> command) {
        this(match, new EndRoute<>(command), new EndRoute<>());
    }

    /**
     * Construct FkRoute that route to match command or spare command if update not match condition.
     *
     * @param match      match condition
     * @param command    match command
     * @param spareRoute spare command
     */
    public FkRoute(final Match<U> match,
                   final Cmd<U, S> command,
                   final Cmd<U, S> spareRoute) {
        this(match, new EndRoute<>(command), new EndRoute<>(spareRoute));
    }

    /**
     * Construct FkRoute that route to match route or none if update not match condition.
     *
     * @param match match condition
     * @param route match route
     */
    public FkRoute(final Match<U> match,
                   final Route<U, S> route) {
        this(match, route, new EndRoute<>());
    }

    /**
     * Main constructor. Construct FkRoute that route to match route or spare route if update not match condition.
     *
     * @param match      match condition
     * @param route      match route
     * @param spareRoute spare route
     */
    public FkRoute(final Match<U> match,
                   final Route<U, S> route,
                   final Route<U, S> spareRoute) {
        this.match = match;
        this.route = route;
        this.spareRoute = spareRoute;
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> updWrap) {
        if (!match.match(updWrap)) {
            return spareRoute.route(updWrap);
        }

        return this.route.route(updWrap);
    }
}
