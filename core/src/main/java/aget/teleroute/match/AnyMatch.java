package aget.teleroute.match;

import aget.teleroute.update.UpdateWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Check update match any condition.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class AnyMatch<SrcUpdate> implements Match<SrcUpdate> {
    private final Collection<Match<SrcUpdate>> matches;

    /**
     * Construct AnyMatch contains one or many conditions.
     *
     * @param matches conditions
     */
    @SafeVarargs
    public AnyMatch(final Match<SrcUpdate>... matches) {
        this(Arrays.asList(matches));
    }

    /**
     * Main constructor. Construct AnyMatch contains collection of conditions.
     *
     * @param matches conditions
     */
    public AnyMatch(final Collection<Match<SrcUpdate>> matches) {
        this.matches = Collections.unmodifiableCollection(matches);
    }

    @Override
    public boolean match(UpdateWrap<SrcUpdate> updateWrap) {
        return this.matches
                .stream()
                .anyMatch(match -> match.match(updateWrap));
    }
}
