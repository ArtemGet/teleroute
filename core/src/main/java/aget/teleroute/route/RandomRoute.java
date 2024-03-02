package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Collection<Route<SrcUpdate, Sender>> routes;

    @SafeVarargs
    public RandomRoute(final Cmd<SrcUpdate, Sender>... commands) {
        this(
                Arrays.stream(commands)
                        .map(EndRoute::new)
                        .collect(Collectors.<Route<SrcUpdate, Sender>>toList())
        );
    }

    @SafeVarargs
    public RandomRoute(final Route<SrcUpdate, Sender>... route) {
        this(Arrays.asList(route));
    }

    public RandomRoute(final Collection<Route<SrcUpdate, Sender>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Send<Sender>> route(Update<SrcUpdate> update) {
        return routes.stream()
                .skip(new Random().nextInt(routes.size()))
                .findFirst()
                .flatMap(route -> route.route(update));
    }
}
