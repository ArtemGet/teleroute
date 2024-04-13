package aget.teleroute.match;

import aget.teleroute.update.UpdWrap;

public class FkMatch implements Match<String> {
    private final Boolean match;

    /**
     * Always match
     */
    public FkMatch() {
        this(true);
    }

    public FkMatch(Boolean match) {
        this.match = match;
    }

    @Override
    public Boolean match(UpdWrap<String> updWrap) {
        return this.match;
    }
}
