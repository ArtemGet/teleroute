package aget.teleroute.route;

import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Iterate over routes, pick first successful result.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class IterateRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Collection<Route<SrcUpdate, Sender>> routes;

    /**
     * Construct IterateRoute that iterate over one or many routes.
     *
     * @param routes routes to iterate
     */
    @SafeVarargs
    public IterateRoute(final Route<SrcUpdate, Sender>... routes) {
        this(Arrays.asList(routes));
    }

    /**
     * Main constructor. Construct IterateRoute that iterate over collection of routes.
     *
     * @param routes routes to iterate
     */
    public IterateRoute(final Collection<Route<SrcUpdate, Sender>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Send<Sender>> route(Update<SrcUpdate> update) {
        return Optional.ofNullable(update)
                .flatMap(
                        upd -> routes.stream()
                                .map(route -> route.route(upd))
                                .filter(Optional::isPresent)
                                .findFirst()
                                .orElse(Optional.empty())
                );
    }
}
