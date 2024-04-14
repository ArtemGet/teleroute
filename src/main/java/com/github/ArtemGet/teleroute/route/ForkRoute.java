package com.github.ArtemGet.teleroute.route;

import com.github.ArtemGet.teleroute.command.Cmd;
import com.github.ArtemGet.teleroute.match.Match;
import com.github.ArtemGet.teleroute.update.UpdWrap;

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
public final class ForkRoute<U, S> implements Route<U, S> {
    private final Route<U, S> origin;
    private final Route<U, S> spare;
    private final Match<U> match;

    /**
     * Construct FkRoute that routes to match command or none if update not match condition.
     *
     * @param match   match condition
     * @param origin match command
     */
    public ForkRoute(final Match<U> match, final Cmd<U, S> origin) {
        this(match, new EndRoute<>(origin), new EndRoute<>());
    }

    /**
     * Construct FkRoute that route to match command or spare command if update not match condition.
     *
     * @param match      match condition
     * @param origin    match command
     * @param spare spare command
     */
    public ForkRoute(final Match<U> match, final Cmd<U, S> origin, final Cmd<U, S> spare) {
        this(match, new EndRoute<>(origin), new EndRoute<>(spare));
    }

    /**
     * Construct FkRoute that route to match route or none if update not match condition.
     *
     * @param match match condition
     * @param origin match route
     */
    public ForkRoute(final Match<U> match, final Route<U, S> origin) {
        this(match, origin, new EndRoute<>());
    }

    /**
     * Main constructor. Construct FkRoute that route to match route or spare route if update not match condition.
     *
     * @param match      match condition
     * @param origin      match route
     * @param spare spare route
     */
    public ForkRoute(final Match<U> match, final Route<U, S> origin, final Route<U, S> spare) {
        this.match = match;
        this.origin = origin;
        this.spare = spare;
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        if (!match.match(update)) {
            return spare.route(update);
        }
        return this.origin.route(update);
    }
}
