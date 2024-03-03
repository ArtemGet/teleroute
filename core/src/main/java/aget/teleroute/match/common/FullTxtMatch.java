package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.UpdateWrap;

/**
 * Check update's message's text equals provided text.
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class FullTxtMatch<SrcUpdate> implements Match<SrcUpdate> {
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
    public Boolean match(final UpdateWrap<SrcUpdate> updateWrap) {
        return updateWrap.text()
                .map(text -> text.equals(this.text))
                .orElse(false);
    }
}
