package aget.teleroute.match;

import aget.teleroute.update.UpdateWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Check update match all condition.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class AllMatch<SrcUpdate> implements Match<SrcUpdate> {
    private final Collection<Match<SrcUpdate>> matches;

    /**
     * Construct AllMatch contains one or many conditions.
     *
     * @param matches conditions
     */
    @SafeVarargs
    public AllMatch(final Match<SrcUpdate>... matches) {
        this(Arrays.asList(matches));
    }

    /**
     * Main constructor. Construct AllMatch contains collection of conditions.
     *
     * @param matches conditions
     */
    public AllMatch(final Collection<Match<SrcUpdate>> matches) {
        this.matches = Collections.unmodifiableCollection(matches);
    }

    @Override
    public boolean match(UpdateWrap<SrcUpdate> updateWrap) {
        return this.matches.stream()
                .allMatch(match -> match.match(updateWrap));
    }
}
