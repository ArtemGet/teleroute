package aget.teleroute.command;


import aget.teleroute.send.Send;

import java.util.Optional;

public interface Cmd<SrcUpdate, Sender> {
    Optional<Send<Sender>> execute(SrcUpdate update);
}
