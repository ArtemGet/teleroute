package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.UpdateWrap;

/**
 * Check provided text occur in update's message's text
 *
 * @param <SrcUpdate> telegram update, i.e. telegrambots Update or your own telegram update implementation
 */
public final class OccurTxtMatch<SrcUpdate> implements Match<SrcUpdate> {
    private final String text;

    /**
     * Main constructor. Construct OccurTxtMatch.
     *
     * @param text text to match
     */
    public OccurTxtMatch(String text) {
        this.text = text;
    }

    @Override
    public boolean match(UpdateWrap<SrcUpdate> updateWrap) {
        return updateWrap.text()
                .map(text -> text.contains(this.text))
                .orElse(false);
    }
}
