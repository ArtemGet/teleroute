package aget.teleroute.route;


import aget.teleroute.send.Send;
import aget.teleroute.update.Update;

import java.util.Optional;

public interface Route<SrcUpdate, Sender> {
    Optional<Send<Sender>> route(Update<SrcUpdate> update);
}
