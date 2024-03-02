package aget.teleroute.send;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Impl of batch send, use in case you need to send many messages at the same time
 *
 * @param <Sender> Actually sends messages, ie AdsSender from telegrambots or your own impl of tg send
 */
public final class MultiSend<Sender> implements Send<Sender> {
    private final Collection<Send<Sender>> sends;

    /**
     * Construct MultiSend of one or many Send objects.
     *
     * @param sends one or many Send objects
     */
    @SafeVarargs
    public MultiSend(final Send<Sender>... sends) {
        this(Arrays.asList(sends));
    }

    /**
     * Main constructor. Construct MultiSend of collection of Send objects.
     *
     * @param sends collection of Send objects
     */
    public MultiSend(final Collection<Send<Sender>> sends) {
        this.sends = Collections.unmodifiableCollection(sends);
    }

    @Override
    public void send(Sender sender) {
        this.sends.forEach(send -> send.send(sender));
    }
}
