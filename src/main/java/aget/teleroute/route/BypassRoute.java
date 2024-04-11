package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.update.UpdWrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Bypass error route.
 * Lead to route or command, in case of Exception been thrown route to other route or command or none.
 *
 * <p><img src="../doc-files/ErrorRouteScheme.png" width=1000>
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class BypassRoute<U, S> implements Route<U, S> {
    private static final Logger log = LoggerFactory.getLogger(BypassRoute.class);
    private final Route<U, S> route;
    private final Route<U, S> errorRoute;

    /**
     * Construct InterceptErrRoute that route to target route and none if error.
     *
     * @param command target command
     */
    public BypassRoute(final Cmd<U, S> command) {
        this(new EndRoute<>(command));
    }

    /**
     * Construct InterceptErrRoute that route to target command and error command if error.
     *
     * @param command      target command
     * @param errorCommand error command
     */
    public BypassRoute(final Cmd<U, S> command,
                       final Cmd<U, S> errorCommand) {
        this(new EndRoute<>(command), new EndRoute<>(errorCommand));
    }

    /**
     * Construct InterceptErrRoute that route to target route and none if error.
     *
     * @param route target route
     */
    public BypassRoute(final Route<U, S> route) {
        this(route, new EndRoute<>());
    }

    /**
     * Main constructor. Construct InterceptErrRoute that route to target route and error route if error.
     *
     * @param route      target route
     * @param errorRoute error route
     */
    public BypassRoute(final Route<U, S> route,
                       final Route<U, S> errorRoute) {
        this.route = route;
        this.errorRoute = errorRoute;
    }

    @Override
    public Optional<Cmd<U, S>> route(final UpdWrap<U> updWrap) {
        try {
            return this.route.route(updWrap);
        } catch (Exception e) {
            log.error("Error occurred while executing command: {}", e.getMessage(), e);
            return this.errorRoute.route(updWrap);
        }
    }
}
