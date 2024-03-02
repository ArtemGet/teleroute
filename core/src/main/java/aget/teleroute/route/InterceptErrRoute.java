package aget.teleroute.route;


import aget.teleroute.command.Cmd;
import aget.teleroute.command.SkipCmd;
import aget.teleroute.send.Send;
import aget.teleroute.update.Update;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @param <SrcUpdate>
 * @param <Sender>
 */
public final class InterceptErrRoute<SrcUpdate, Sender> implements Route<SrcUpdate, Sender> {
    private static final Logger log = LoggerFactory.getLogger(InterceptErrRoute.class);
    private final Route<SrcUpdate, Sender> route;
    private final Route<SrcUpdate, Sender> errorRoute;

    public InterceptErrRoute(final Cmd<SrcUpdate, Sender> command) {
        this(command, new SkipCmd<>());
    }

    public InterceptErrRoute(final Cmd<SrcUpdate, Sender> command,
                             final Cmd<SrcUpdate, Sender> errorCommand) {
        this(new EndRoute<>(command), new EndRoute<>(errorCommand));
    }

    public InterceptErrRoute(final Route<SrcUpdate, Sender> route) {
        this(route, new EndRoute<>());
    }

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
