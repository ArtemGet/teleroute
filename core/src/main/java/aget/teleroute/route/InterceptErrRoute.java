package aget.teleroute.route;

import aget.teleroute.command.Cmd;
import aget.teleroute.command.SkipCmd;
import aget.teleroute.send.Send;
import aget.teleroute.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Lead to route or command, in case of Exception been thrown route to other route or command or none.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class InterceptErrRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private static final Logger log = LoggerFactory.getLogger(InterceptErrRoute.class);
    private final Route<SrcUpdate, Sender> route;
    private final Route<SrcUpdate, Sender> errorRoute;

    /**
     * Construct InterceptErrRoute that route to target route and none if error.
     *
     * @param command target command
     */
    public InterceptErrRoute(final Cmd<SrcUpdate, Sender> command) {
        this(command, new SkipCmd<>());
    }

    /**
     * Construct InterceptErrRoute that route to target command and error command if error.
     *
     * @param command      target command
     * @param errorCommand error command
     */
    public InterceptErrRoute(final Cmd<SrcUpdate, Sender> command,
                             final Cmd<SrcUpdate, Sender> errorCommand) {
        this(new EndRoute<>(command), new EndRoute<>(errorCommand));
    }

    /**
     * Construct InterceptErrRoute that route to target route and none if error.
     *
     * @param route target route
     */
    public InterceptErrRoute(final Route<SrcUpdate, Sender> route) {
        this(route, new EndRoute<>());
    }

    /**
     * Main constructor. Construct InterceptErrRoute that route to target route and error route if error.
     *
     * @param route      target route
     * @param errorRoute error route
     */
    public InterceptErrRoute(final Route<SrcUpdate, Sender> route,
                             final Route<SrcUpdate, Sender> errorRoute) {
        this.route = route;
        this.errorRoute = errorRoute;
    }

    @Override
    public Optional<Send<Sender>> route(Update<SrcUpdate> update) {
        try {
            return this.route.route(update);
        } catch (Exception e) {
            log.error("Error occurred while executing command: {}", e.getMessage(), e);
            return this.errorRoute.route(update);
        }
    }
}
