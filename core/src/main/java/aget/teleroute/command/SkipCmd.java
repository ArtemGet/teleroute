package aget.teleroute.command;


import aget.teleroute.send.Send;

import java.util.Optional;

public class SkipCmd<SrcUpdate, Sender> implements Cmd<SrcUpdate, Sender> {
    @Override
    public Optional<Send<Sender>> execute(SrcUpdate srcUpdate) {
        return Optional.empty();
    }
}
