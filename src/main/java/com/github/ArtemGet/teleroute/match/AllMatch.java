package com.github.ArtemGet.teleroute.match;

import com.github.ArtemGet.teleroute.update.UpdWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Check update match all condition.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class AllMatch<U> implements Match<U> {
    private final Collection<Match<U>> matches;

    /**
     * Construct AllMatch contains one or many conditions.
     *
     * @param matches conditions
     */
    @SafeVarargs
    public AllMatch(final Match<U>... matches) {
        this(Arrays.asList(matches));
    }

    /**
     * Main constructor. Construct AllMatch contains collection of conditions.
     *
     * @param matches conditions
     */
    public AllMatch(final Collection<Match<U>> matches) {
        this.matches = Collections.unmodifiableCollection(matches);
    }

    @Override
    public Boolean match(final UpdWrap<U> update) {
        return this.matches.stream()
                .allMatch(match -> match.match(update));
    }
}
