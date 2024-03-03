package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.send.Send;
import aget.teleroute.update.UpdateWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Pick any random route or command.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class RandomRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private final Collection<Route<SrcUpdate, Sender>> routes;

    /**
     * Construct RandomRoute with one or many commands.
     *
     * @param commands one or many commands
     */
    @SafeVarargs
    public RandomRoute(final Cmd<SrcUpdate, Sender>... commands) {
        this(
                Arrays.stream(commands)
                        .map(EndRoute::new)
                        .collect(Collectors.<Route<SrcUpdate, Sender>>toList())
        );
    }

    /**
     * Construct RandomRoute with one or many routes.
     *
     * @param routes one or many routes
     */
    @SafeVarargs
    public RandomRoute(final Route<SrcUpdate, Sender>... routes) {
        this(Arrays.asList(routes));
    }

    /**
     * Main constructor. Construct RandomRoute with many routes.
     *
     * @param routes routes
     */
    public RandomRoute(final Collection<Route<SrcUpdate, Sender>> routes) {
        this.routes = Collections.unmodifiableCollection(routes);
    }

    @Override
    public Optional<Send<Sender>> route(final UpdateWrap<SrcUpdate> updateWrap) {
        return routes.stream()
                .skip(new Random().nextInt(routes.size()))
                .findFirst()
                .flatMap(route -> route.route(updateWrap));
    }
}
