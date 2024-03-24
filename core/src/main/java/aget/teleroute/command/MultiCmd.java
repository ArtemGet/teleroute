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
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class MultiCmd<U, S> implements Cmd<U, S> {
    private final Collection<Cmd<U, S>> commands;

    /**
     * Construct MultiCmd that execute one or many commands.
     *
     * @param commands commands to execute
     */
    @SafeVarargs
    public MultiCmd(final Cmd<U, S>... commands) {
        this(Arrays.asList(commands));
    }

    /**
     * Main constructor. Construct MultiCmd that execute collection of commands.
     *
     * @param commands commands to execute
     */
    public MultiCmd(final Collection<Cmd<U, S>> commands) {
        this.commands = Collections.unmodifiableCollection(commands);
    }

    @Override
    public Optional<Send<S>> execute(final U update) {
        return Optional.of(
                new MultiSend<>(
                        commands.stream()
                                .map(cmd -> cmd.execute(update))
                                .filter(Optional::isPresent)
                                .map(Optional::get)
                                .collect(Collectors.toList())
                )
        );
    }
}
