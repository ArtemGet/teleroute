package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.Update;

public class OccureTxtMatch<SrcUpdate> implements Match<SrcUpdate> {
    private final String text;

    public OccureTxtMatch(String text) {
        this.text = text;
    }

    @Override
    public boolean match(Update<SrcUpdate> update) {
        return update.text()
                .map(text -> text.contains(this.text))
                .orElse(false);
    }
}
