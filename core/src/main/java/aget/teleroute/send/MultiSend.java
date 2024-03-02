package aget.teleroute.send;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public final class MultiSend<Sender> implements Send<Sender> {
    private final Collection<Send<Sender>> sends;

    @SafeVarargs
    public MultiSend(final Send<Sender>... sends) {
        this(Arrays.asList(sends));
    }

    public MultiSend(final Collection<Send<Sender>> sends) {
        this.sends = Collections.unmodifiableCollection(sends);
    }

    @Override
    public void send(Sender sender) {
        this.sends.forEach(send -> send.send(sender));
    }
}
