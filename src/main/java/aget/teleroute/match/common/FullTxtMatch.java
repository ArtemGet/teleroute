package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.UpdWrap;

/**
 * Check update's message's text equals provided text.
 *
 * @param <U> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class FullTxtMatch<U> implements Match<U> {
    private final String text;

    /**
     * Main constructor. Construct FullTxtMatch.
     *
     * @param text text to match
     */
    public FullTxtMatch(final String text) {
        this.text = text;
    }

    @Override
    public Boolean match(final UpdWrap<U> update) {
        return update.text()
                .map(text -> text.equals(this.text))
                .orElse(false);
    }
}
