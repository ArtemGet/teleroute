package aget.teleroute.match;

import aget.teleroute.update.Update;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public final class AnyMatch<SrcUpdate> implements Match<SrcUpdate> {
    private final Collection<Match<SrcUpdate>> matches;

    @SafeVarargs
    public AnyMatch(final Match<SrcUpdate>... matches) {
        this(Arrays.asList(matches));
    }

    public AnyMatch(final Collection<Match<SrcUpdate>> matches) {
        this.matches = Collections.unmodifiableCollection(matches);
    }

    @Override
    public boolean match(Update<SrcUpdate> update) {
        return this.matches
                .stream()
                .anyMatch(match -> match.match(update));
    }
}
