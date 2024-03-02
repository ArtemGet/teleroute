package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.Update;

public class FullTxtMatch<SrcUpdate> implements Match<SrcUpdate> {
    private final String text;

    public FullTxtMatch(String text) {
        this.text = text;
    }

    @Override
    public boolean match(Update<SrcUpdate> update) {
        return update.text()
                .map(text -> text.equals(this.text))
                .orElse(false);
    }
}
