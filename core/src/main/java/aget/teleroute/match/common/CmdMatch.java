package aget.teleroute.match.common;

import aget.teleroute.match.Match;
import aget.teleroute.update.Update;

/**
 *
 * @param <SrcUpdate>
 */
public final class CmdMatch<SrcUpdate> implements Match<SrcUpdate> {
    @Override
    public boolean match(Update<SrcUpdate> update) {
        return update.isCommand();
    }
}
