package aget.teleroute.route;

import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

public final class IterateRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Collection<Route<SrcUpdate, Sender>> routes;

    @SafeVarargs
    public IterateRoute(final Route<SrcUpdate, Sender>... routes) {
        this(Arrays.asList(routes));
    }

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
