package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.UpdWrap;

/**
 * Check provided text occur in update's message's text
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class OccurTxtMatch<U> implements Match<U> {
    private final String text;

    /**
     * Main constructor. Construct OccurTxtMatch.
     *
     * @param text text to match
     */
    public OccurTxtMatch(final String text) {
        this.text = text;
    }

    @Override
    public Boolean match(final UpdWrap<U> updWrap) {
        return updWrap.text()
                .map(text -> text.contains(this.text))
                .orElse(false);
    }
}
