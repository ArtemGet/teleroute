package aget.teleroute.match;

import aget.teleroute.update.UpdWrap;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Check update match any condition.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class AnyMatch<U> implements Match<U> {
    private final Collection<Match<U>> matches;

    /**
     * Construct AnyMatch contains one or many conditions.
     *
     * @param matches conditions
     */
    @SafeVarargs
    public AnyMatch(final Match<U>... matches) {
        this(Arrays.asList(matches));
    }

    /**
     * Main constructor. Construct AnyMatch contains many conditions.
     *
     * @param matches conditions
     */
    public AnyMatch(final Collection<Match<U>> matches) {
        this.matches = Collections.unmodifiableCollection(matches);
    }

    @Override
    public Boolean match(UpdWrap<U> updWrap) {
        if (this.matches.isEmpty()) {
            return true;
        }

        return this.matches
                .stream()
                .anyMatch(match -> match.match(updWrap));
    }
}
