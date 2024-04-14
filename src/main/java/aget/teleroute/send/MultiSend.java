package aget.teleroute.send;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Implementation of batch send, use in case you need to send many messages at the same time.
 *
 * @param <S> Actually sends messages, ie AdsSender from telegrambots or your own impl of tg send
 */
public final class MultiSend<S> implements Send<S> {
    private static final Logger log = LoggerFactory.getLogger(MultiSend.class);
    private final Collection<Send<S>> sends;

    /**
     * Construct MultiSend of one or many Send objects.
     *
     * @param sends one or many Send objects
     */
    @SafeVarargs
    public MultiSend(final Send<S>... sends) {
        this(Arrays.asList(sends));
    }

    /**
     * Main constructor. Construct MultiSend of many Send objects.
     *
     * @param sends collection of Send objects
     */
    public MultiSend(final Collection<Send<S>> sends) {
        this.sends = Collections.unmodifiableCollection(sends);
    }

    @Override
    public void send(final S client) {
        this.sends.forEach(send -> {
            try {
                send.send(client);
            } catch (Exception e) {
                log.warn("Unable to send message: {}", e.getMessage(), e);
            }
        });
    }
}
