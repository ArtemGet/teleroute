package aget.teleroute.command;

import aget.teleroute.send.MultiSend;
import aget.teleroute.send.Send;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

public class MultiCmd<SrcUpdate, Sender> implements Cmd<SrcUpdate, Sender> {
    private final Collection<Cmd<SrcUpdate, Sender>> commands;

    @SafeVarargs
    public MultiCmd(Cmd<SrcUpdate, Sender>... commands) {
        this(Arrays.asList(commands));
    }

    public MultiCmd(Collection<Cmd<SrcUpdate, Sender>> commands) {
        this.commands = commands;
    }

    @Override
    public Optional<Send<Sender>> execute(SrcUpdate srcUpdate) {
        return Optional.of(
                new MultiSend<>(
                        commands.stream()
                                .map(cmd -> cmd.execute(srcUpdate))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList())
                )
        );
    }
}
