package aget.teleroute.command;

import aget.teleroute.send.MultiSend;
import aget.teleroute.send.Send;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Execute many commands as one.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class MultiCmd<SrcUpdate, Sender> implements Cmd<SrcUpdate, Sender> {
    private final Collection<Cmd<SrcUpdate, Sender>> commands;

    /**
     * Construct MultiCmd that execute one or many commands.
     *
     * @param commands commands to execute
     */
    @SafeVarargs
    public MultiCmd(final Cmd<SrcUpdate, Sender>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Main constructor. Construct MultiCmd that execute collection of commands.
     *
     * @param commands commands to execute
     */
    public MultiCmd(final Collection<Cmd<SrcUpdate, Sender>> commands) {
        this.commands = Collections.unmodifiableCollection(commands);
    }

    @Override
    public Optional<Send<Sender>> execute(final SrcUpdate srcUpdate) {
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
