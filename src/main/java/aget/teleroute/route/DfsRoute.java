package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.update.UpdWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Depth first search route.
 * Iterate over routes, pick first successful result.
 *
 * <p><img src="../doc-files/IteratorRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class DfsRoute<U, S> implements Route<U, S> {
    private final Collection<Route<U, S>> routes;

    /**
     * Construct IterateRoute that iterate over one or many routes.
     *
     * @param routes routes to iterate
     */
    @SafeVarargs
    public DfsRoute(final Route<U, S>... routes) {
        this(Arrays.asList(routes));
    }

    /**
     * Main constructor. Construct IterateRoute that iterate over many routes.
     *
     * @param routes routes to iterate
     */
    public DfsRoute(final Collection<Route<U, S>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> update) {
        return Optional.ofNullable(update)
                .flatMap(
                        upd -> this.routes.stream()
                                .map(route -> route.route(upd))
                                .filter(Optional::isPresent)
                                .findFirst()
                                .orElse(Optional.empty())
                );
    }
}
