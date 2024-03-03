package aget.teleroute.command;

import aget.teleroute.send.Send;

import java.util.Optional;

/**
 * Does nothing, skip logic.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 * @param <Sender>    sends messages, i.e. telegrambots AdsSender or your own telegram send** implementation
 */
public final class SkipCmd<SrcUpdate, Sender> implements Cmd<SrcUpdate, Sender> {
    @Override
    public Optional<Send<Sender>> execute(final SrcUpdate srcUpdate) {
        return Optional.empty();
    }
}
