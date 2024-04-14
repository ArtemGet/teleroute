package com.github.ArtemGet.teleroute.match;

import com.github.ArtemGet.teleroute.update.UpdWrap;

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
    public Boolean match(UpdWrap<String> update) {
        return this.match;
    }
}
