package aget.teleroute.command;

import aget.teleroute.send.MultiSend;
import aget.teleroute.send.Send;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Execute many commands as one.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <S> sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class MultiCmd<U, S> implements Cmd<U, S> {
    private static final Logger log = LoggerFactory.getLogger(MultiCmd.class);
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
        return this.form(this.executeCmds(update));
    }

    private Optional<Send<S>> form(List<Send<S>> sends) {
        if (sends.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new MultiSend<>(sends));
    }

    private List<Send<S>> executeCmds(final U update) {
        return this.commands.stream()
                .map(cmd -> {
                    try {
                        return cmd.execute(update);
                    } catch (Exception e) {
                        log.warn("Error execute cmd: {}", e.getMessage(), e);
                        return Optional.<Send<S>>empty();
                    }
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
